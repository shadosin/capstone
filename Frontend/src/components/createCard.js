export default function createCard(headerText, contentText) {
  const container = document.createElement("div");
  container.className = "container card";

  const cardHeader = document.createElement("header");
  cardHeader.className = "card-header";

  const h5 = document.createElement("h5");
  h5.textContent = headerText;
  cardHeader.append(h5);

  const cardContent = document.createElement("div");
  cardContent.className = "card-content";
  cardContent.textContent = contentText;

  container.append(cardHeader, cardContent);
  return container;
}
