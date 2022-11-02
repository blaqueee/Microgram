let user = {}
const LS_USER = 'user'

checkLocalStorage()

let showSplashButton = document.getElementById("showSplashButton")
let loginForm = document.getElementById('login-form')

showSplashButton.addEventListener('click', () => {
    showSplashButton.hidden = true
    showSplashScreen()
})

loginForm.addEventListener('submit', (event) => {
    authenticate(event)
})

function authenticate(event) {
    event.preventDefault()
    let form = event.target
    let userFormData = new FormData(form)
    let user = Object.fromEntries(userFormData)
    axios.post(BASE_URL + '/login', user)
    .then((response) => {
        user['id'] = response.data
        saveUserToLocalStorage(user)
        let navbar = createNavbarElement()
        addNavbarElement(navbar)
        hideSplashScreen()
        getPosts()
        addListenerForLogout()
    })
    .catch((error) => {
        let splash = document.getElementById('splash')
        splash.getElementsByClassName('login-error')[0].hidden = false
    })

}

function showSplashScreen() {
    let nav = document.getElementById('nav')
    let body = document.getElementsByClassName("main-content")[0]
    body.classList.remove('main-body')
    let splash = document.getElementById("splash")
    nav.hidden = true
    body.hidden = true
    splash.hidden = false
}

function hideSplashScreen() {
    let nav = document.getElementById('nav')
    let body = document.getElementsByClassName("main-content")[0]
    body.classList.add('main-body')
    let splash = document.getElementById("splash")
    splash.hidden = true
    nav.hidden = false
    body.hidden = false
}

function sendAuthorizedPostRequest(url, body, options) {
    let updatedOptions = addAuthorizationHeader(options)
    return axios.post(url, body, updatedOptions)
}

function addAuthorizationHeader(options) {
    if (!ifUserExists()) {
        showSplashButton.hidden = true
        showSplashScreen()
        return
    }
    let user = getUserFromLocalStorage()
    options.headers['Authorization'] = 'Basic ' + btoa(user.username + ':' + user.password)
    return options
}

function ifUserExists() {
    return localStorage.getItem('user') != null
}

function saveUserToLocalStorage(user) {
    const userAsJSON = JSON.stringify(user)
    localStorage.setItem(LS_USER, userAsJSON);
}

function getUserFromLocalStorage() {
    const userAsJSON = localStorage.getItem(LS_USER);
    const user = JSON.parse(userAsJSON);
    return user;
}

function createNavbarElement() {
    let user = getUserFromLocalStorage()
    let navbar = document.createElement('div')
    navbar.classList.add('header')
    navbar.setAttribute('id', 'nav')
    navbar.innerHTML = `
        <div class="logo">
          Microgram
        </div>
        <div class="menu">
            <a href="#" class="link">
                <div class="title">${user.username}</div>
                <div class="bar"></div>
            </a>
            <a href="register.html" class="link signup">
                <div class="title">Sign up</div>
                <div class="bar"></div>
            </a>
            <button id="logout-button" href="#" class="link logout">
                <div class="title">Log out</div>
                <div class="bar"></div>
            </button>
        </div>
    `
    navbar.hidden = true
    return navbar;
}

function addNavbarElement(navbar) {
    document.body.prepend(navbar)
}

function checkLocalStorage() {
    if (getUserFromLocalStorage() != null) {
        user = getUserFromLocalStorage()
        let navbar = createNavbarElement(user)
        addNavbarElement(navbar)
        hideSplashScreen()
        addListenerForLogout()
        getPosts()
    } 
}

function addListenerForLogout() {
    let logout = document.getElementById('logout-button')
    logout.addEventListener('click', (event) => {
        logOut(event)
    })
}

function logOut(event) {
    event.preventDefault();
    localStorage.removeItem(LS_USER)
    showSplashScreen()
}