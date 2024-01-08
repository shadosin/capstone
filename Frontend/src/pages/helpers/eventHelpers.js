import ApiClient from "../../api/apiClient";
import { DateTime } from "luxon";
import { createDialog } from "./createDialog";
import { toggleModal } from "./modal";

const client = new ApiClient();

// Get scrollbar width

export function renderEvents() {
  try {
    const eventsArea = document.querySelectorAll(".eventSection");
    if (eventsArea) {
      for (const element of eventsArea) element.remove();
    }
  } catch (error) {
    console.error(error);
  }

  const eventsArea = document.querySelector("#Events");
  const articleEvents = document.createElement("article");
  articleEvents.classList.add("eventSection");
  const buttonGrp = createBtnGroup();
  eventsArea.append(buttonGrp, articleEvents);
  const addEventBtn = document.querySelector("#addEventBtn");
  const dialog = createDialog("modal-add-event", "Add Event");
  addEventBtn.append(dialog);
}

export function createBtnGroup() {
  const btnGroup = document.createElement("article");
  btnGroup.classList.add("btn-group", "eventSection");
  const ul = document.createElement("ul");
  const li = document.createElement("li");
  const button = document.createElement("button");
  button.classList.add("btn");
  button.setAttribute("data-target", "modal-add-event");
  button.setAttribute("id", "addEventBtn");
  button.append(document.createTextNode("Add Event"));
  button.addEventListener("click", (event) => {
    toggleModal(event);
  });

  li.append(button);
  ul.append(li);
  btnGroup.append(ul);

  return btnGroup;
}

export function createEventListItem(data) {
  const { eventId, start, end, title, description } = data;
  const listItem = document.createElement("li");
  listItem.setAttribute("eventId", eventId);
  listItem.setAttribute("start", start);
  listItem.setAttribute("end", end);
  listItem.setAttribute("title", title);
  listItem.setAttribute("description", description);
  const text = document.createElement("p");
  text.innerHTML = `${DateTime.fromISO(start).toLocaleString(
    DateTime.TIME_SIMPLE,
  )} - ${DateTime.fromISO(end).toLocaleString(DateTime.TIME_SIMPLE)}: ${title}`;
  listItem.append(text);
  return listItem;
}

export function createEventList(listData, listItemClass, defaultText) {
  const list = document.createElement("ul");
  if (!listData || listData.length === 0) {
    listItem.classList.add(listItemClass);
    list.append(listItem);
  } else {
    for (let data of listData) {
      const listItem = createEventListItem(data);
      listItem.classList.add(listItemClass);
      list.append(listItem);
    }
  }
  return list;
}
