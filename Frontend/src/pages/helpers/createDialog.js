import * as modal from "./modal.js";
import { addScheduleForm } from "../addScheduleForm";
import { addEventForm } from "../addEventForm";
const { toggleModal } = modal;

export function createDialog(modalId, modalTitle, modalContent) {
  // Create the dialog element
  const dialog = document.createElement("dialog");
  dialog.id = `${modalId}`;

  // Create the article element
  const article = document.createElement("div");
  article.classList.add("container", "dialogContent");

  // Create the close button
  const closeButton = document.createElement("a");
  closeButton.href = "#close";
  closeButton.ariaLabel = "Close";
  closeButton.classList.add("close");
  closeButton.dataset.target = `${modalId}`;
 //closeButton.addEventListener("click", toggleModal);

  // Create the heading
  const heading = document.createElement("h3");
  heading.textContent = `${modalTitle || "Modal Title"}`;

  // Create the paragraph
  const modalContentArea = document.createElement("div");
  modalContentArea.classList.add("modalContentArea");
  modalContentArea.id = "modalContentArea";

  if (modalContent === "addScheduleForm") {
    modalContentArea.innerHTML = addScheduleForm();
  }
  if (modalContent === "addEventForm") {
    modalContentArea.innerHTML = addEventForm();
  }
  else {
    modalContentArea.textContent = "No Content Provided.";
  }

  const footer = document.createElement("footer");

  article.append(heading, closeButton, modalContentArea, footer);
  dialog.append(article);

  return dialog;
}
