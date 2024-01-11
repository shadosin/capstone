import Chart from "chart.js/auto";
import ApiClient from "../../api/apiClient";

const client = new ApiClient();

function getLabel(metric) {
  switch (metric) {
    case "weight":
      return "Weight";
    case "totalCalorieIntake":
      return "Total Calorie Intake";
    case "totalCalorieExpenditure":
      return "Total Calorie Expenditure";
    case "carbs":
      return "Carbs";
    case "fats":
      return "Fats";
    case "protein":
      return "Protein";
    default:
      return "Unknown";
  }
}

function getData() {
  const userId = JSON.parse(window.sessionStorage.getItem("userInfo")).userId;

  const metrics =
    JSON.parse(window.localStorage.getItem("metrics")) ||
    client
      .getUserMetrics(userId)
      .then((r) => {
        window.localStorage.setItem("metrics", JSON.stringify(r));
      })
      .then((r) => {
        return r;
      });
  delete metrics.userId;
  delete metrics["weightUnit"];
  // metrics.weight = 200;
  // metrics.protein = 50;
  // metrics.fats = 100;
  // metrics.carbs = 200;
  // metrics.totalCalorieExpenditure = 300;
  // metrics.totalCalorieIntake = 400;

  let output = [];

  for (const metric in metrics) {
    output.push({
      label: getLabel(metric),
      metric: metric,
      value: metrics[metric],
    });
  }
  ;
  return output;
}

export function metricChart() {
  /*
  {
    "userId": "60d10d11-857d-40ae-b5a3-861ce580adbb",
    "weight": 0,
    "weightUnit": "KG",
    "totalCalorieIntake": 0,
    "totalCalorieExpenditure": 0,
    "carbs": 0,
    "fats": 0,
    "protein": 0
}
 */
  const data = getData();

  new Chart(document.getElementById("metricChartCanvas"), {
    type: "bar",
    data: {
      labels: data.map((item) => item.label),
      datasets: [
        {
          label: "Metrics",
          data: data.map((item) => item.value),
          backgroundColor: [
            "rgba(255, 99, 132, 0.6)",
            "rgba(255, 159, 64, 0.6)",
            "rgba(255, 205, 86, 0.6)",
            "rgba(75, 192, 192, 0.6)",
            "rgba(54, 162, 235, 0.6)",
            "rgba(153, 102, 255, 0.6)",
          ],
          borderColor: [
            "rgb(255, 99, 132)",
            "rgb(255, 159, 64)",
            "rgb(255, 205, 86)",
            "rgb(75, 192, 192)",
            "rgb(54, 162, 235)",
            "rgb(153, 102, 255)",
          ],
          borderWidth: 1,
        },
      ],
    },
    options: {
      legend: { display: false },
      title: {
        display: true,
        text: "Metrics",
      },
    },
  });
}
