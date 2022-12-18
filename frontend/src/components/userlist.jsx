import React from "react";
import "../css/userlist.css";

const UserList = ({ users }) => {
  return (
    <React.Fragment>
      <div className="title">사용자</div>
      <ul className="ul">
        {users.map((user) => (
          <li className="userlist" key={user.id}>
            {user.nickname}
          </li>
        ))}
      </ul>
    </React.Fragment>
  );
};

export default UserList;
