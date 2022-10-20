const ChatPresenter = ({ contents, message, setMessage, handleEnter }) => {
  return (
    <div className={"chat-box"}>
      <div>
        <form onSubmit={handleEnter}>
          <input
            placeholder="input your messages..."
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onSubmit={handleEnter}
          />
        </form>
      </div>
      <div className={"contents"}>
        {contents.map((message, idx) => (
          <div key={idx}>{message.content} </div>
        ))}
      </div>
    </div>
  );
};

export default ChatPresenter;
