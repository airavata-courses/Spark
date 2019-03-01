const request = options => {
  const headers = new Headers({
    "Content-Type": "application/json"
  });


  const defaults = { headers: headers };
  options = Object.assign({}, defaults, options);

  return fetch(options.url, options).then(response =>
    response.json().then(json => {
      if (!response.ok) {
        return Promise.reject(json);
      }
      return json;
    })
  );
};

export function getAllServices() {
  return request({
    url: "http://149.165.169.102:5000/services/discoverAll/",
    method: "GET"
  });
}

export function getServiceByName(serviceName) {
  return request({
    url: "http://149.165.169.102:5000/services/discover/?name=" + serviceName,
    method: "GET"
  });
}
