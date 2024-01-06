import createPage from "../components/createPage";
import ApiClient from "../api/apiClient";
import { DateTime } from "luxon";

const listOfDays = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

function createList(listData, listItemClass, defaultText) {
  const list = document.createElement("ul");
  if (!listData || listData.length === 0) {
    const listItem = createListItem(defaultText);
    listItem.classList.add(listItemClass);
    list.append(listItem);
  } else {
    for (let data of listData) {
      const listItem = createListItem(data);
      listItem.classList.add(listItemClass);
      list.append(listItem);
    }
  }
  return list;
}

function createListItem(textContent) {
  const listItem = document.createElement("li");
  let text;
  if (typeof textContent === "object") {
    const { scheduleId, text: dates } = textContent;
    listItem.setAttribute("scheduleId", scheduleId);
    text = document.createTextNode(dates);
    listItem.append(text);
  } else {
    text = document.createElement("p");
   text.innerHTML = `${textContent}`;
    listItem.append(text);
  }
  listItem.classList.add("schedule-list-item");

  if (listOfDays.includes(textContent)) {
    listItem.setAttribute("dayIndex", String(listOfDays.indexOf(textContent)));
  }
  return listItem;
}

function createArea(area) {
  const article = document.createElement("article");
  article.setAttribute("id", `${area}`);
  return article;
}

function getCurrentWeekNumber() {
  const currentDate = new Date();
  const startDate = new Date(currentDate.getFullYear(), 0, 1);
  let days = Math.floor((currentDate - startDate) / (24 * 60 * 60 * 1000));

  let weekNumber = Math.ceil(days / 7);

  // Display the calculated result
  console.log("Week number of " + currentDate + " is :   " + weekNumber);
  return weekNumber;
}

function getCurrentWeek() {
  const dt = DateTime.now();
  const dateFromStr = dt.startOf("week");
  const dateToStr = dt.endOf("week");

  return {
    start: dateFromStr,
    end: dateToStr,
  };
}

async function dashboard() {
  const client = new ApiClient();

  createPage("Dashboard");
  const scheduleArea = createArea("Schedules");
  scheduleArea.setAttribute("aria-busy", "true");

  const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
  const userId = userInfo.userId;
  // TODO: Get schedules from API

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

  async function getScheduleData() {
    const userSessionData = window.sessionStorage.getItem("userInfo");
    let data = {};
    if (userSessionData) {
      const { scheduleIdList } = JSON.parse(userSessionData);

      for (const scheduleId of scheduleIdList) {
        await client
          .getOneSchedule(scheduleId)
          .then((response) => {
            const { scheduleId, start, end, scheduledEventIds } = response.data;
            data[scheduleId] = { start, end, scheduledEventIds };
          })
          .then(() => {
            window.localStorage.setItem("scheduleData", JSON.stringify(data));
          });
      }
    }
  }

  await getScheduleData();

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

  scheduleArea.append(scheduleList);

  const pageContent = document.querySelector("#pageContent");
  console.log(pageContent);
  pageContent.append(scheduleArea);

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
    dayArea.setAttribute("aria-busy", "true");
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
    dayArea.append(dayList);
    // dayArea.addEventListener("click", (event) => {
    //   event.preventDefault();
    //   console.log(event.target);
    // });
    dayArea.style.visibility = "visible";
  });
}

function createWeekList(scheduleWeek) {
  const data = [];
  for (const day in scheduleWeek) {
  }
}

async function daysEventsList(day) {
  const scheduleId = "1";
  const dayId = "1";
  const dayEventsList = createList(dayEvents, "day-events-list-item", "");
  return dayEventsList;
}

export default dashboard;
