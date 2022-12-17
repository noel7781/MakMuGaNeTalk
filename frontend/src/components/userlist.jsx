import React from "react";

const UserList = ({ users }) => {
  return (
    <ul>
      {users.map((user) => (
        <li key={user.id}>{user.nickname}</li>
      ))}
    </ul>
  );
};

export default UserList;
