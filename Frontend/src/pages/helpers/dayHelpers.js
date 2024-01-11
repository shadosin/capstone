import { createArea, createList } from "../dashboardHelpers";
import { DateTime } from "luxon";
import { renderEvents } from "./eventHelpers";

export function getDayList(event) {
  const scheduleId = event.target.getAttribute("scheduleId");
  let lst = [];
  const scheduleItem = JSON.parse(window.localStorage.getItem("scheduleData"))[
    scheduleId
  ];
  const { start } = scheduleItem;
  for (let i = 0; i < 7; i++) {
    let currentDate = DateTime.fromISO(start)
      .plus({ days: i })
      .toLocaleString(DateTime.DATE_HUGE);
    let shortDate = DateTime.fromISO(start)
      .plus({ days: i })
      .toLocaleString(DateTime.DATE_SHORT);
    const index = currentDate.indexOf(",");
    const dayName = currentDate.slice(0, index);
    const text = `${dayName} \n ${shortDate}`;
    lst.push(text);
  }
  return lst;
}

export function renderDays(event) {
  try {
    const dayArea = document.querySelector("#Days");
    if (dayArea) {
      dayArea.remove();
    }
  } catch (error) {
    console.error(error);
  }

  const dayArea = createArea("Days");
  const pageContent = document.querySelector("#pageContent");
  pageContent.append(dayArea);
  const dayList = getDayList(event);
  const dayHTMLList = createList(dayList, "schedule-day-list-item", "");
  const articleDayList = document.createElement("article");
  articleDayList.append(dayHTMLList);
  dayArea.append(articleDayList);

  articleDayList.addEventListener("click", (event) => {

    const dayIndex = event.target.getAttribute("dayindex");
    window.sessionStorage.setItem("dayIndex", dayIndex)
    const day = dayList[dayIndex];
    renderEvents(day);
  });
}
