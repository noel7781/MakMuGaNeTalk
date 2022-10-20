import { Outlet } from "react-router-dom";

const Root = () => {
  return (
    <div>
      <h1>MakMuGaNe Talk</h1>
      Root
      <Outlet />
    </div>
  );
};

export default Root;
