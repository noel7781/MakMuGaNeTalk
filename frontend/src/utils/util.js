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

export const isExpired = ({ exp }) => {
  return Date.now() >= exp * 1000;
};

export const getArrays = (a, b) => {
  return Array.from({ length: b - a + 1 }, (v, k) => k + a);
};
