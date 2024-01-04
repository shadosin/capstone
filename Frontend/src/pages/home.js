import createCard from "../components/createCard";

function home() {
  const section = document.querySelector("#app");
  // Create the grid div
  const grid = document.createElement("div");
  grid.className = "grid";

  // Create the first card
  const card1 = createCard(
    "Track your meals",
    "Track your meals with VitaTrac. You can add your own meals or use the ones we provide.",
  );

  // Create the second card
  const card2 = createCard(
    "Track your exercises",
    "Track your exercises with VitaTrac. You can add your own exercises or use the ones we provide.",
  );

  // Append cards to the grid
  grid.append(card1, card2);

  // Append the grid to the document body or a specific container
  section.append(grid); // or append it to a specific container
}

export default home;
