let registerForm = document.getElementById('register-form')
registerForm.addEventListener('submit', (event) => {
    registerUser(event)
})

const BASE_URL = 'http://localhost:8181'

function registerUser(event) {
    event.preventDefault()
    let form = event.target
    let data = new FormData(form)
    let object = {}
    data.forEach((value, key) => {
        object[key] = value
    })
    sendRegisterForm(object)
}

function sendRegisterForm(user) {
    axios.post(BASE_URL + '/register', user)
    .then((response) => {
        let data = response.data
        alert('You have been successfully registered!')
        window.location.href = 'index.html'
    })
    .catch((error) => {
        alert(error.response.data)
    })
}