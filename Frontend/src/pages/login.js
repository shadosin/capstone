export default function login() {
  const section = document.querySelector("section");

  const div = document.createElement("div");
  div.classList.add("container");

  const card = document.createElement("div");
  card.classList.add("card");

  card.innerHTML = `<h2>Login Page</h2>`;

  div.append(card);
  section.append(div);
}
