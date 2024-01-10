import { DateTime } from "luxon";
import ApiClient from "../api/apiClient";
import {getCurrentWeek} from "./helpers/scheduleHelpers";

const client = new ApiClient();

export const listOfDays = [
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
  "Sunday",
];

export function createList(listData, listItemClass, defaultText) {
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

export function createListItem(textContent) {
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

export function createArea(area) {
  const article = document.createElement("div");
  const articleHeading = document.createElement("article");
  const areaHeading = document.createElement("h4");
  areaHeading.innerHTML = area;
  articleHeading.append(areaHeading);
  article.append(articleHeading);
  article.setAttribute("id", `${area}`);
  return article;
}

export async function getScheduleData() {
  let userSessionData = JSON.parse( window.sessionStorage.getItem("userInfo"));
  let data = {};
  if (userSessionData) {
    let { scheduleIdList } = userSessionData

    if (scheduleIdList.length === 0) {
      scheduleIdList =  await client.createSchedule(userSessionData.userId, getCurrentWeek()).data
      await client.updateUserInfo(userSessionData.userId)
      }
    console.log(scheduleIdList)
    for (const scheduleId of scheduleIdList) {
      await client
        .getOneSchedule(scheduleId)
        .then((response) => {
          const { scheduleId, start, end, scheduledEventIds } = response.data;
          data[scheduleId] = { start, end, scheduledEventIds };
        })
        .then(() => {
          window.localStorage.setItem("scheduleData", JSON.stringify(data));
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }
}


