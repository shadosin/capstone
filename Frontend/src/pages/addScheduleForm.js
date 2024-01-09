import * as scheduleHelpers from "./helpers/scheduleHelpers";
import ApiClient from "../api/apiClient";
import { DateTime } from "luxon";

const client = new ApiClient();

async function createSchedule(weekToSchedule) {
  const userId = window.sessionStorage.getItem("userInfo")[userId];

  if (!weekToSchedule) {
    weekToSchedule = scheduleHelpers.getCurrentWeek();
  }
  await client.createSchedule(userId, weekToSchedule).then(async () => {
    await client.updateUserInfo(userId);
  });

  await client
    .getCurrentSchedule(userId)
    .then((response) => {
      console.log(response.data);
    })
    .catch((error) => {
      console.log(error);
    });
}

function getStartAndEndDate(weekValue) {
  if (weekValue) {
    // Split the value into year and week number
    const [year, week] = weekValue.split("-W");

    // Get the first day of the week (assuming Monday as the first day)
    const startDate = DateTime.fromObject(
      {
        weekYear: parseInt(year),
        weekNumber: parseInt(week),
        weekday: 1,
      },
      { zone: "America/Chicago" },
    );

    // Get the last day of the week (Sunday)
    const endDate = startDate.plus({ days: 6 });

    return {
      start: startDate.toISO(), // Converts to ISO date format (YYYY-MM-DD)
      end: endDate.toISO(),
    };
  }

  return { start: null, end: null };
}

window.addScheduleSubmit = async function (event) {
  event.preventDefault();
  let week = event.target.week.value;
  let formDataObject = getStartAndEndDate(week);
  const userId = JSON.parse(window.sessionStorage.getItem("userInfo")).userId;
  formDataObject = {
    ...formDataObject,
    userId,
  };

  try {
    await client
      .createSchedule(userId, formDataObject)
      .then(async (response) => {
        await client.updateUserInfo(userId);
        window.localStorage.setItem(
          "ScheduleIDList",
          JSON.stringify(response.data),
        );
      })
      .then(() => {
        window.location.href = "/";
      });
  } catch (error) {
    console.error(error);
    const form = document.querySelector("#addScheduleForm");
    form.reset();
  }
};

export function addScheduleForm() {
  return `
<form id="addScheduleForm" onsubmit="addScheduleSubmit(event)">
    <div class="modalformInput">
        <label for="start">Start Date:</label>
        <input type="week" id="week" name="Week" placeholder="Week to Add">
    </div>
    <div class = "btnGroup, footer">
        <input id="#submitBtn" type="submit" value="Submit">
    </div>
</form>
`;
}

//
//  const modalContentArea = document.querySelector("#modalContentArea");
//
//  console.log("addScheduleForm")
//  const addScheduleForm = document.createElement("form");
//  addScheduleForm.id = "addScheduleForm";
//
//  const fields = document.createElement("div");
//
//  function createInputField(id, label, type, placeholder, isRequired) {
//    const field = document.createElement("div");
//    const labelElement = document.createElement("label");
//    labelElement.htmlFor = id;
//    labelElement.textContent = label + ":";
//
//    const inputElement = document.createElement("input");
//    inputElement.type = type;
//    inputElement.id = id;
//    inputElement.name = id;
//    inputElement.placeholder = placeholder;
//    if (isRequired) inputElement.required = true;
//
//    if (id === "email") {
//      field.classList.add("emailAddressInput");
//    } else {
//      field.classList.add("formInput");
//    }
//    field.append(labelElement, inputElement);
//    fields.append(field);
//  }
//
//
// createInputField("start", "Start Date", "date", "Start Date", true);
//
//
//  const buttonGroup = document.createElement("div");
//  buttonGroup.classList.add("btnGroup");
//
//  const canceltBtn = document.createElement("button");
//  canceltBtn.innerText = `Cancel`;
//  canceltBtn.classList.add("outline", "secondary");
//  canceltBtn.addEventListener("click", (event) => {
//      toggleModal(event);
//  });
//
//  const submitBtn = document.createElement("input");
//  submitBtn.setAttribute("type", "submit");
//  buttonGroup.append(canceltBtn, submitBtn);
//  addScheduleForm.append(buttonGroup);
//  modalContentArea.append(addScheduleForm);
//  addScheduleForm.addEventListener("submit", submitForm);
//  async function submitForm(event) {
//  event.preventDefault()
//    const userId = JSON.parse(window.sessionStorage.getItem("userInfo")).userId;
//  let formData = new FormData(event.target);
//  formData.append("userId", userId);
//
//  try {
//
//    const response = await client.createSchedule(client.createPayload(formData));
//    if (response) {
//      window.localStorage.setItem("ScheduleIDList", JSON.stringify(response.data));
//      window.location.href = "/";
//    }
//  } catch (error) {
//    console.error(error);
//    const form = document.querySelector("#addScheduleForm");
//    form.reset();
//  }
//
//  }
//

