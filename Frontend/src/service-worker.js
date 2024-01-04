import { precacheAndRoute, createHandlerBoundToURL } from "workbox-precaching";

console.log(`⚙️ Service Worker`);

workbox.routing.registerRoute(
  ({ request }) => request.destination === "image",
  new workbox.strategies.NetworkFirst(),
);

workbox.skipWaiting();
