import ApiClient from "../api/apiClient";
import { DateTime } from "luxon";

const client = new ApiClient();

window.addEventSubmit = async function (event) {
  event.preventDefault();
  const eventItemCard = document.querySelector(".eventItemCard");
  const dayIndex = window.sessionStorage.getItem("dayIndex");

  const scheduleListId = JSON.parse(
    window.localStorage.getItem("scheduleData"),
  )[dayIndex];
  const userId = JSON.parse(window.sessionStorage.getItem("userInfo")).userId;

  const form = document.querySelector("#addEventForm");
  const formData = new FormData(form);

  const formDataObj = Object.fromEntries(formData.entries());
  const time = DateTime.fromISO(
    `${formDataObj.date}T${formDataObj.time}`,
  ).toISO();

  const payload = {
    scheduledDateTime: time,
    userId,
  };

  if (formDataObj.type === "Meal") {
    payload.eventType = "MEAL";
  }
  if (formDataObj.type === "Exercise") {
    payload.eventType = "EXERCISE";
  }

  if (formDataObj.type === "Meal") {
    payload.mealId = formDataObj.mealSelected;
  }
  if (formDataObj.type === "Exercise") {
    payload.exerciseId = formDataObj.exerciseSelected;
  }

  await client.createScheduleEvent(payload, scheduleListId).then(async (response) => {
    return await client.updateSchedule(response);
  });
};

window.renderEventItemOnForm = function (eventItem) {
  function generateListItems(eventDetails) {
    if (eventDetails) {
      return Object.entries(eventDetails).map(([key, value]) => {
        let output = "";
        if (key === "name") {
          output += `<h4><strong>${value}</strong></h4>`;
        } else {
          output += `<li><strong>${key} : </strong>${value}</li>`;
        }
        return output;
      });
    }
  }

  const eventItemCard = document.querySelector("#eventItemCard");
  if (eventItemCard) {
    eventItemCard.remove();
  }

  const submitEventButton = document.querySelector("#submitEventButton");

  if (submitEventButton) {
    submitEventButton.remove();
  }

  const formElement = document.querySelector("#addEventForm");
  const div = document.createElement("div");
  div.id = "eventItemCard";
  div.classList.add("eventItemCard", "card");

  if (eventItem) {
    console.log({ eventItem });
    const itemId = eventItem.mealId || eventItem.exerciseId;
    div.setAttribute("id", itemId);
    const { name } = eventItem;
    div.innerHTML = `<h4><strong>${name}</strong></h4>`;
  }
  div.innerHTML = `<ul>${generateListItems(eventItem)}</ul>`;

  const submitButton = document.createElement("input");
  submitButton.classList.add("btn");
  submitButton.id = "submitEventButton";
  submitButton.type = "submit";
  formElement.append(div, submitButton);

  const submitButtons = document.querySelectorAll("#submitEventButton");

  if (submitButtons.length > 1) {
    submitButtons[0].remove();
  }
};

window.onEventSelect = async function (event) {
  const eventItemCard = document.querySelector("#eventItemCard");
  const submitEventButton = document.querySelector("#submitEventButton");

  if (submitEventButton) {
    submitEventButton.remove();
  }

  if (eventItemCard) {
    const eventItemCard = document.querySelector("#eventItemCard");
    if (eventItemCard) {
      eventItemCard.remove();
    }
  }

  let eventItem;
  if (event.target.id === "mealType") {
    const mealSelected = document.querySelector("#mealSelected").value;
    if (mealSelected) {
      eventItem = await client.getMealEvent(mealSelected);
    }
  }

  if (event.target.id === "exerciseType") {
    const exerciseSelected = document.querySelector("#exerciseSelected").value;
    if (exerciseSelected) {
      eventItem = await client.getExerciseEvent(exerciseSelected);
    }
  }

  renderEventItemOnForm(eventItem);
};

window.onTypeSelect = async function (event) {
  const formElement = document.querySelector("#addEventForm");
  const eventItemCard = document.querySelector("#eventItemCard");

  const submitEventButton = document.querySelector("#submitEventButton");

  if (submitEventButton) {
    submitEventButton.remove();
  }
  if (eventItemCard) {
    eventItemCard.remove();
  }

  const mealSelect = document.querySelector("#mealSelected");
  if (mealSelect) {
    mealSelect.remove();
  }

  const exerciseSelect = document.querySelector("#exerciseSelected");
  if (exerciseSelect) {
    exerciseSelect.remove();
  }

  const selectElement = document.createElement("select");

  if (event.target.id === "mealType") {
    try {
      const response = await client.getAllMealEvents(event.target.value);
      selectElement.id = "mealSelected";
      selectElement.name = "mealSelected";
      selectElement.required = true;
      selectElement.innerHTML = `<option value="" disabled selected>Select a Meal</option>`;
      selectElement.innerHTML = response
        .map((meal) => {
          return `<option id=${meal.mealId} value=${meal.mealId}>${meal.name}</option>`;
        })
        .join("");
    } catch (error) {
      console.error("Error in getAllMealEvents:", error);
    }
  }

  if (event.target.id === "exerciseType") {
    try {
      const response = await client.getAllExerciseEvents(event.target.value);
      selectElement.id = "exerciseSelected";
      selectElement.name = "exerciseSelected";
      selectElement.required = true;
      selectElement.innerHTML = response;
      selectElement.innerHTML = `<option value="" disabled selected>Select an Exercise</option>`;
      selectElement.innerHTML += response
        .map((exercise) => {
          return `<option id=${exercise.exerciseId} value=${exercise.exerciseId}>${exercise.name}</option>`;
        })
        .join("");
    } catch (error) {
      console.error("Error in getAllExerciseEvents:", error);
    }
  }

  formElement.append(selectElement);
  selectElement.addEventListener("click", () => {
    selectElement.addEventListener("change", onEventSelect(event));
  });
};

window.eventOnClick = function (event) {
  const eventItemCard = document.querySelector("#eventItemCard");

  if (eventItemCard) {
    eventItemCard.remove();
  }

  const submitEventButton = document.querySelector("#submitEventButton");

  if (submitEventButton) {
    submitEventButton.remove();
  }

  const data = document.getElementById("data");
  const element = document.querySelector("#addEventForm");

  const select = document.querySelector(".eventType");
  if (select) {
    select.remove();
  }
  const mealSelect = document.querySelector("#mealSelected");
  if (mealSelect) {
    mealSelect.remove();
  }
  const exerciseSelect = document.querySelector("#exerciseSelected");
  if (exerciseSelect) {
    exerciseSelect.remove();
  }

  const mealTypes = ["Breakfast", "Lunch", "Dinner", "Snack"];
  const exerciseTypes = ["Cardio", "Strength", "Flexibility"];
  const typeSelect = document.createElement("select");
  typeSelect.classList.add("eventType");
  typeSelect.required = true;

  if (event.target.value === "Meal") {
    typeSelect.id = "mealType";
    typeSelect.name = "mealType";
    typeSelect.innerHTML = `<option value="" disabled selected>Select a Meal Type</option>`;
    typeSelect.innerHTML += mealTypes
      .map((mealType) => {
        return `<option value="${mealType}">${mealType}</option>`;
      })
      .join("");
  } else if (event.target.value === "Exercise") {
    typeSelect.id = "exerciseType";
    typeSelect.name = "exerciseType";
    typeSelect.innerHTML =
      `<option value="" disabled selected>Select a Exercise Type</option>` +
      exerciseTypes
        .map((exerciseType) => {
          return `<option value="${exerciseType}">${exerciseType}</option>`;
        })
        .join("");
  }

  element.append(typeSelect);
  typeSelect.addEventListener("click", () => {
    typeSelect.addEventListener("change", onTypeSelect);
  });
};

export function addEventForm() {
  const submitEventButton = document.querySelector("#submitEventButton");

  if (submitEventButton) {
    submitEventButton.remove();
  }

  return `
    <div class="add-event-form">
      <form id="addEventForm" onsubmit="addEventSubmit(event)">
        <label for="time">Time</label>
        <input type="time" id="time" name="time" required>
        <label for="date" >Date</label>
        <input type="date" id="date" name="date" required>
      <select id="type" name="type" required onclick="eventOnClick(event)">
        <option value="Meal">Meal</option>
         <option value="Exercise">Exercise</option>
         </select>
         
         <label id="data" for="data"></label>
         
        </form>
    </div>
  `;
}
