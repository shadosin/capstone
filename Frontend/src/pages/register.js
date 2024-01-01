function registerPage() {
  const section = document.querySelector("section");
  const div = document.createElement("div");
  div.classList.add("container-fluid", "card");
  const pageHeading = document.createElement("h2");
  pageHeading.innerText = `Register Page`;
  div.append(pageHeading);
  section.append(div);
}

export default registerPage;
