import React from "react";
import ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ErrorPage from "./error-page";
import { CookiesProvider } from "react-cookie";
import { Provider } from "react-redux";
import store from "./Store";
import Loading from "./components/ui/loading";

const Root = React.lazy(() => import("./routes/root"));
const Index = React.lazy(() => import("./routes"));
const Main = React.lazy(() => import("./routes/main"));
const SignUp = React.lazy(() => import("./routes/signup"));
const Mychat = React.lazy(() => import("./routes/mychat"));
const Groupchat = React.lazy(() => import("./routes/groupchat"));
const ChatContainer = React.lazy(() => import("./components/chatContainer"));
const InviteAlarm = React.lazy(() => import("./routes/invitealarm"));

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        errorElement: <ErrorPage />,
        children: [
          {
            index: true,
            element: (
              <React.Suspense fallback={<Loading />}>
                <Index />
              </React.Suspense>
            ),
          },
          {
            path: "signin",
            errorElement: <div>Login Error!</div>,
          },
          {
            path: "main",
            element: (
              <React.Suspense fallback={<Loading />}>
                <Main />
              </React.Suspense>
            ),
          },
          {
            path: "signup",
            element: (
              <React.Suspense fallback={<Loading />}>
                <SignUp />
              </React.Suspense>
            ),
          },
          {
            path: "my-chat",
            element: (
              <React.Suspense fallback={<Loading />}>
                <Mychat />
              </React.Suspense>
            ),
          },
          {
            path: "group-chat",
            element: (
              <React.Suspense fallback={<Loading />}>
                <Groupchat />
              </React.Suspense>
            ),
          },
          {
            path: "chatRooms/:chatRoomId",
            element: (
              <React.Suspense fallback={<Loading />}>
                <ChatContainer />
              </React.Suspense>
            ),
          },
          {
            path: "invite",
            element: (
              <React.Suspense fallback={<Loading />}>
                <InviteAlarm />
              </React.Suspense>
            ),
          },
        ],
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")).render(
  <React.StrictMode>
    <CookiesProvider>
      <Provider store={store}>
        <RouterProvider router={router} />
      </Provider>
    </CookiesProvider>
  </React.StrictMode>
);
