import ApiClient from "../../api/apiClient";
import { DateTime } from "luxon";
import { createList, getScheduleData } from "../dashboardHelpers";
import { renderDays } from "./dayHelpers";
import { toggleModal } from "./modal";
import { createDialog } from "./createDialog";
import { addScheduleForm } from "../addScheduleForm";

const client = new ApiClient();

function createScheduleList() {
  const scheduleDataList = JSON.parse(
    window.localStorage.getItem("scheduleData"),
  );
  let lst = [];
  for (const schedule in scheduleDataList) {
    const { start, end } = scheduleDataList[schedule];
    lst.push({
      scheduleId: schedule,
      text: `${DateTime.fromISO(start).toLocaleString(
        DateTime.DATE_SHORT,
      )} - ${DateTime.fromISO(end).toLocaleString(DateTime.DATE_SHORT)}`,
    });
  }
  return createList(lst, "schedule-list-item", "No schedules found");
}

export function getCurrentWeek() {
  const dt = DateTime.now();
  const dateFromStr = dt.startOf("week");
  const dateToStr = dt.endOf("week");
  return {
    start: dateFromStr,
    end: dateToStr,
  };
}

function createBtnGroup() {
  const btnGroup = document.createElement("article");
  btnGroup.classList.add("btn-group", "ScheduleSection");
  const ul = document.createElement("ul");
  const li = document.createElement("li");
  const button = document.createElement("button");
  button.classList.add("btn");
  button.setAttribute("data-target", "modal-add-schedule");
  button.setAttribute("id", "addScheduleBtn");
  button.append(document.createTextNode("Add Schedule"));

  function handlebuttonClick(event) {
    toggleModal(event);
    console.log("removed button event listener");
    button.removeEventListener("click", handlebuttonClick);
  }

  button.addEventListener("click", handlebuttonClick);
  li.append(button);
  ul.append(li);
  btnGroup.append(ul);

  return btnGroup;
}

export async function renderSchedules() {
  try {
    await getScheduleData();
  } catch (error) {
    console.log(error);
  }
  const scheduleArea = document.querySelector("#Schedules");
  const buttonGrp = createBtnGroup();
  scheduleArea.append(buttonGrp);
  const addScheduleBtn = document.querySelector("#addScheduleBtn");
  const dialog = createDialog(
    "modal-add-schedule",
    "Add Schedule",
    "addScheduleForm",
  );
  addScheduleBtn.append(dialog);

  const scheduleList = createScheduleList();
  scheduleList.setAttribute("id", "scheduleList");

  const newElement = document.createElement("article");
  newElement.append(scheduleList);
  document.querySelector("#Schedules").append(newElement);

  scheduleList.addEventListener("click", (event) => {
    event.preventDefault();
    renderDays(event);
  });
}
