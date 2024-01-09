import ApiClient from "../../api/apiClient";
import {metricChart} from "./metricChart";

const client = new ApiClient();

async function getMetrics() {
  try {
    const userInfo = JSON.parse(window.sessionStorage.getItem("userInfo"));
    if (!userInfo || !userInfo.userId) {
      console.error("User info is not available");
      return "Error: User info is not available";
    }

    const userId = userInfo.userId;
    const metricsData = await client.getUserMetrics(userId);

    if (metricsData) {
      console.log(metricsData);
      return metricsData;
    } else {
      return "Error: No data received";
    }
  } catch (error) {
    console.error("Error in getMetrics:", error);
    return "Error";
  }
}

export async function renderMetrics() {
  const metricsArea = document.querySelector("#Metrics");
  const articleMetrics = document.createElement("article");
  articleMetrics.setAttribute("id", "metricChartArea");
  metricsArea.append(articleMetrics);
  const canvas = document.createElement("canvas");
  canvas.setAttribute("id", "metricChartCanvas");
  articleMetrics.append(canvas);
  document.getElementById("metricChartCanvas").setAttribute("aria-busy", "true");
  await getMetrics().then(() => {
    metricChart();
    document.getElementById("metricChartCanvas").setAttribute("aria-busy", "false");
  })
}
