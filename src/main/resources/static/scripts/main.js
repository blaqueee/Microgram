const user = {
    id: 2,
    username: "blaque",
    email: "kairat@gmail.com",
    password: "12345678",
    authorized: false
};

const comment = {
    id: 1,
    text: "That's great!",
    time: "2022-20-10T15:30:00",
    commentator: {
        id: 1,
        username: "blaque",
        email: "kairat@gmail.com",
        authorized: true
    }
}

let showSplashButton = document.getElementById("showSplashButton")
let authorizeButton = document.getElementById("authorize")
showSplashButton.addEventListener('click', () => {
    showSplashButton.hidden = true
    showSplashScreen()
})
authorizeButton.addEventListener('click', (event) => {
    authorize(event)
})

function authorize(event) {
    event.preventDefault()
    var email = document.getElementById("username").value
    var password = document.getElementById("password").value
    if (user['username'] === email && user['password'] === password) {
        user['authorized'] = true
        hideSplashScreen()
        getPosts()
        showSplashButton.hidden = false
        return
    }
    alert("Вы не авторизованы: username должен быть blaque, пароль 12345678");
}

function showSplashScreen() {
    let body = document.getElementsByClassName("main-content")[0]
    let splash = document.getElementById("splash")
    body.hidden = true
    splash.hidden = false
}

function hideSplashScreen() {
    let body = document.getElementsByClassName("main-content")[0]
    let splash = document.getElementById("splash")
    splash.hidden = true
    body.hidden = false
}