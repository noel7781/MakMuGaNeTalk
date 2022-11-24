import "../css/chatRoom.css";

const ChatPresenter = ({
  contents,
  message,
  setMessage,
  handleSubmit,
  username,
}) => {
  console.log(contents);
  console.log(username);
  console.log(message);
  const handleEnter = (e) => {
    if (e.keyCode == 13)
      if (!e.shiftKey) {
        e.preventDefault();
        handleSubmit(e);
      }
  };
  return (
    <div className="outline">
      <div className="messageBox">
        {contents.map((message, idx) => (
          <div className="messages" key={idx}>
            {message.content}{" "}
          </div>
        ))}
      </div>
      <form className="send" onSubmit={handleSubmit}>
        <textarea
          className="send_text"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyDown={handleEnter}
        ></textarea>
        <button className="send_button" type="submit">
          전송
        </button>
      </form>
    </div>
  );
};

export default ChatPresenter;