import React from "react";
import { useLocation, useNavigate } from "react-router-dom";

const InviteAlarm = () => {
  const { state } = useLocation();
  const { inviteList } = state;
  const navigate = useNavigate();
  const onClickInviteUrl = (url) => {
    navigate(url);
  };
  // inv.content, inv.notificationType, inv.senderNickname, inv.url
  return (
    <div>
      {inviteList &&
        inviteList.map((inv, i) => {
          return (
            <div key={i} onClick={() => onClickInviteUrl(inv.url)}>
              {inv.senderNickname}이 초대를 보냈습니다. {inv.content}
            </div>
          );
        })}
    </div>
  );
};

export default InviteAlarm;
