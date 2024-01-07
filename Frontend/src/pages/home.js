import createCard from "../components/createCard";
import createPage from "../components/createPage";

function home() {
  createPage("Welcome to VitaTrac");
  const section = document.querySelector("#pageContent");
  // Create the grid div
  const grid = document.createElement("div");
  grid.className = "grid";

  // Create the first card
  const card1 = createCard(
    "Meal Tracking",
    "Track your meals with VitaTrac. We have an extensive list of meals to choose from, even some that are gluten free or vegan.",
  );
  card1.setAttribute("id", "meals");
  const card3 = createCard();

  const img = document.createElement("img");
  img.setAttribute("src", "assets/vegetables.jpg");
  img.setAttribute("alt", "vegetables");
  img.setAttribute("id", "veggies");
  // Create the second card

  const img2 = document.createElement("img");
  img2.setAttribute("src", "assets/kettlebell.jpg");
  img2.setAttribute("alt", "kettlebell");
  img2.setAttribute("id", "kettlebell");

  const card2 = createCard(
    "Exercise Tracking",
    "Track your exercises with VitaTrac. We provide a large list of exercises that are tailored to all types of exercise.",
  );

  // Append cards to the grid
  grid.append(card1, card2);
  //     mealCard.append(img);
  //   //card3.append(img);

  // Append the grid to the document body or a specific container
  section.append(grid); // or append it to a specific container
  const mealCard = document.querySelector("#meals > .card-content");
  mealCard.firstElementChild.before(img);

const exerciseCard = document.querySelector(".grid:last-child")
exerciseCard.lastElementChild.lastElementChild.before(img2);
}
export default home;
