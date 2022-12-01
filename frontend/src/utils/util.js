export const timeConvert = (times) => {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
  };
  if (Array.isArray(times)) {
    return new Date(...times.slice(0, -1)).toLocaleTimeString("ko", {
      options,
    });
  }
  return new Date(times).toLocaleTimeString("ko", {
    options,
  });
};
