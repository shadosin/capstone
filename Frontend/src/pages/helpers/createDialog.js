import * as modal from "./modal.js";

const { toggleModal } = modal;

export function createDialog(modalId, modalTitle, modalContent) {
  // Create the dialog element
  const dialog = document.createElement("dialog");
  dialog.id = `${modalId}`;

  // Create the article element
  const article = document.createElement("article");

  // Create the close button
  const closeButton = document.createElement("a");
  closeButton.href = "#close";
  closeButton.ariaLabel = "Close";
  closeButton.classList.add("close");
  closeButton.dataset.target = `${modalId}`;
  closeButton.addEventListener("click", toggleModal);

  // Create the heading
  const heading = document.createElement("h3");
  heading.textContent = `${modalTitle || "Modal Title"}`

  // Create the paragraph
  const paragraph = document.createElement("div");
  paragraph.textContent =
    "Cras sit amet maximus risus. Pellentesque sodales odio sit amet augue finibus pellentesque.";
  paragraph.style.color = "black"
  // Create the footer
  const footer = document.createElement("footer");

  // Create the cancel button
  const cancelButton = document.createElement("span");
  cancelButton.href = "#cancel";
  cancelButton.role = "button";
  cancelButton.classList.add("secondary");
  cancelButton.dataset.target = `${modalId}`;
  cancelButton.textContent = "Cancel";
  cancelButton.addEventListener("click", toggleModal);

  // Create the confirm button
  const confirmButton = document.createElement("input");
  confirmButton.href = "#confirm";
  confirmButton.role = "button";
  confirmButton.dataset.target = `${modalId}`;
  confirmButton.textContent = "Submit";
  confirmButton.type = "submit";
  confirmButton.classList.add("btn");
  confirmButton.addEventListener("click", toggleModal);


  const buttonGrp = document.createElement("div");
  buttonGrp.classList.add("btn-group");
  buttonGrp.append(cancelButton, confirmButton);
  footer.append(buttonGrp);
  article.append(heading, closeButton, paragraph, footer);
  dialog.append(article);

  return dialog;
}
