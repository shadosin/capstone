function createPage(pageTitle) {
  const section = document.querySelector("section");
  const page = document.createElement("div");
  page.classList.add("container", "content-section");

  const pageHeading = document.createElement("header");
  pageHeading.classList.add("page-heading");

  const headingText = document.createElement("h2");
  headingText.innerText = `${pageTitle}`;
  pageHeading.append(headingText);

  const pageContent = document.createElement("div");
  pageContent.setAttribute("id", "pageContent");
  pageContent.classList.add("container");

  page.append(pageHeading, pageContent);
  section.append(page);
}

export default createPage;
