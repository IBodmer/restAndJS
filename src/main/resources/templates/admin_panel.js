const navBarList = document.querySelector(".navbar");
const urlPrincipal = "http://localhost:8080/admin/principal";
const urlAllCustomers = "http://localhost:8080/admin"
const headers = {};
fetch(urlAllCustomers)
    .then(response => response.json())
    .then(data => console.log(data));