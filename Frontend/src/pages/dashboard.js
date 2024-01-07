import createPage from "../components/createPage";
import ApiClient from "../api/apiClient";
import { DateTime } from "luxon";
import {
  createArea,
  createList,
  getCurrentWeek,
  getScheduleData,
} from "./dashboardHelpers";

import * as MetricHelpers from "./helpers/metricsHelpers";
import * as EventHelpers from "./helpers/eventHelpers";

async function dashboard() {
  const client = new ApiClient();

  createPage("Dashboard");
  const scheduleArea = createArea("Schedules");
  const dayArea = createArea("Days");
  const eventArea = createArea("Events");
  const metricsArea = createArea("Metrics");

  dayArea.setAttribute("aria-hidden", "true");
  eventArea.setAttribute("aria-hidden", "true");
  metricsArea.setAttribute("aria-hidden", "true");

  const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
  const userId = userInfo.userId;
  const currentWeek = getCurrentWeek();

  // await client.createSchedule(userId, currentWeek).then(async () => {
  //   await client.updateUserInfo(userId);
  // });

  // await client
  //   .getCurrentSchedule(userId)
  //   .then((response) => {
  //     console.log(response.data);
  //   })
  //   .then(() => {
  //     scheduleArea.setAttribute("aria-busy", "false");
  //   })
  //   .catch((error) => {
  //     console.log(error);
  //   });

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

  const scheduleList = createList(
    lst,
    "schedule-list-item",
    "No schedules found",
  );
  const newElement = document.createElement("article");
  newElement.append(scheduleList);
  const pageContent = document.querySelector("#pageContent");
  pageContent.append(scheduleArea, dayArea, eventArea, metricsArea);
  document.querySelector("#Schedules").append(newElement);
  // scheduleArea
  //   .querySelector("article:last-child")
  //   .setAttribute("aria-busy", "true");
  await getScheduleData();

  MetricHelpers.renderMetrics();
  EventHelpers.renderEvents();

  scheduleArea.addEventListener("click", (event) => {
    try {
      const dayArea = document.querySelector("#Days");
      if (dayArea) {
        dayArea.remove();
      }
    } catch (error) {
      console.error(error);
    }

    event.preventDefault();
    // do some stuff
    const dayArea = createArea("Days");
    // dayArea.setAttribute("aria-busy", "true");
    const pageContent = document.querySelector("#pageContent");
    pageContent.append(dayArea);
    const scheduleId = event.target.getAttribute("scheduleId");
    let lst = [];
    const scheduleItem = JSON.parse(
      window.localStorage.getItem("scheduleData"),
    )[scheduleId];
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
    console.log(lst);
    const dayList = createList(lst, "schedule-day-list-item", "");
    const articleDayList = document.createElement("article");
    articleDayList.append(dayList);
    // articleDayList.setAttribute("aria-busy", "true");
    dayArea.append(articleDayList);
    // dayArea.addEventListener("click", (event) => {
    //   event.preventDefault();
    //   console.log(event.target);
    // });
    dayArea.style.visibility = "visible";
  });
}

export default dashboard;
