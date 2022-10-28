const post = {
    id: 1,
    image: "1.jpg",
    description: "just nonsense",
    time: "2022-20-10T15:30:00",
    poster: {
        id: 1,
        username: "blaque",
        email: "kairat@gmail.com",
        authorized: true
    },
    liked: false,
    comments: []
}

// =========================================================
function likePostOnArray() {
    var id = document.getElementById('postIdForLike').value
    id = parseInt(id, 10)
    postArray.forEach(post => {
        if (post['id'] === id) {
            post['liked'] = true
            console.log(post)
            return
        }
    })
}

function dislikePostOnArray() {
    var id = document.getElementById('postIdForDislike').value
    id = parseInt(id, 10)
    postArray.forEach(post => {
        if (post['id'] === id) {
            post['liked'] = false
            console.log(post)
        }
    })
}

function addPostToArray(post) {
    let counter = postArray.length
    let newPost = Object.assign({}, post)
    newPost['id'] = counter + 1
    postArray.push(newPost);
    console.log(postArray)
}
let postArray = [];
let addPostElementButton = document.getElementById("addPostElementButton")
let addPostToArrayButton = document.getElementById("addPostToArrayButton")

// addPostToArrayButton.addEventListener('click', () => {
//     addPostToArray(post)
// })
// =========================================================

const BASE_URL = 'http://localhost:8181'

let likeButton = document.getElementById("likeButton")
let dislikeButton = document.getElementById("dislikeButton")

function createPostElement(post) {
    let postElement = document.createElement('div')
    postElement.setAttribute('class', 'post')
    postElement.innerHTML = `
    <div class="post-account-username-block">
        <p class="post-account-username">${post.poster.username}</p>
    </div>
    <div id="post-photo-${post.id}" class="post-photo-block">
        <span class="icon post-photo-like" style="opacity: 0">
            <i class="fa-solid fa-heart"></i>
        </span>
        <img src="../static/images/${post.image}" class="post-photo" alt="...">
    </div>
    <div class="post-body">
        <div class="post-icons">
            <div id="unlike-icon-${post.id}" class="post-like-icon icon">
                <i class="fa-regular fa-heart"></i>
            </div>
            <div id="like-icon-${post.id}" class="post-like-icon-liked icon" hidden>
                <i class="fa-solid fa-heart"></i>
            </div>
            <div id="comments-icon-${post.id}" class="post-comments-icon icon">
                <i class="fa-regular fa-comment"></i>
            </div>
            <div id="unsave-icon-${post.id}" class="post-save-icon icon">
                <i class="fa-regular fa-bookmark"></i>
            </div>
            <div id="save-icon-${post.id}" class="post-save-icon-saved icon" hidden>
                <i class="fa-solid fa-bookmark"></i>
            </div>
        </div>
        <div class="post-description">
            <span>${post.poster.username}</span>
            ${post.description}
        </div>
        <p class="post-time">${post.time}</p>
        <div id="post-comment-form-${post.id}" class="comment-form-block" hidden>
            <hr style="margin: 15px 0;">
            <form class="comment-form">
                <input type="text" name="post_id" value="${post.id}" hidden>
                <input type="text" name="user_id" value="${user.id}" hidden>
                <textarea name="text" placeholder="Добавьте комментарий..." class="comment-form-text" required></textarea>
                <button type="submit" class="send-post-form-button">Отправить</button>
            </form>
        </div>
        <div id="post-${post.id}-comments" hidden>
        </div>
    </div>`
    return postElement
}

function createCommentElement(comment) {
    let commentElement = document.createElement('div')
    commentElement.setAttribute('class', 'comment')
    commentElement.innerHTML = `
    <hr>
    <div class="comment-body">
        <span>${comment.commentator.username}</span>
        ${comment.text}
    </div>
    <p class="comment-time">${comment.time}</p>
    `
    return commentElement
}

function addPost(postElement) {
    let postsDiv = document.getElementsByClassName('posts')[0]
    postsDiv.prepend(postElement)
}

function addComment(postId, commentElement) {
    let commentsBlock = document.getElementById('post-' + postId + '-comments')
    commentsBlock.prepend(commentElement)
}

function createListenersForPost(post) {
    addListenerForPhoto(post.id)
    addListenerForLikeIcon(post.id)
    addListenerForUnlikeIcon(post.id)
    addListenerForSaveIcon(post.id)
    addListenerForUnsaveIcon(post.id)
    addListenerForCommentIcon(post.id)
    addListenerForCommentForm(post.id)
}

function addListenerForPhoto(postId) {
    let photoBlock = document.getElementById('post-photo-' + postId)
    let likeOnPhotoIcon = photoBlock.getElementsByClassName('post-photo-like')[0]
    photoBlock.addEventListener('dblclick', () => {
        fadeOutIn(likeOnPhotoIcon)
        changeLikeStateOfPostById(postId)
    })
}

function addListenerForLikeIcon(postId) {
    let likeIcon = document.getElementById('like-icon-' + postId)
    likeIcon.addEventListener('click', () => {
        changeLikeStateOfPostById(postId)
    })
}

function addListenerForUnlikeIcon(postId) {
    let unlikeIcon = document.getElementById('unlike-icon-' + postId)
    unlikeIcon.addEventListener('click', () => {
        changeLikeStateOfPostById(postId)
    })
}

function addListenerForSaveIcon(postId) {
    let saveIcon = document.getElementById('save-icon-' + postId)
    saveIcon.addEventListener('click', () => {
        changeSaveStateOfPostById(postId)
    })
}

function addListenerForUnsaveIcon(postId) {
    let unsaveIcon = document.getElementById('unsave-icon-' + postId)
    unsaveIcon.addEventListener('click', () => {
        changeSaveStateOfPostById(postId)
    })
}

function addListenerForCommentIcon(postId) {
    let commentIcon = document.getElementById('comments-icon-' + postId)
    commentIcon.addEventListener('click', () => {
        changeCommentStateOfPostById(postId)
    })
}

function addListenerForCommentForm(postId) {
    let postCommentFormBlock = document.getElementById('post-comment-form-' + postId)
    let commentForm = postCommentFormBlock.getElementsByClassName('comment-form')[0]
    commentForm.addEventListener('submit', createComment)
}

function changeLikeStateOfPostById(postId) {
    let likeIcon = document.getElementById('like-icon-' + postId)
    let unlikeIcon = document.getElementById('unlike-icon-' + postId)
    if (likeIcon.hidden) {
        unlikeIcon.hidden = true
        likeIcon.hidden = false
        return
    }
    unlikeIcon.hidden = false
    likeIcon.hidden = true
}

function changeSaveStateOfPostById(postId) {
    let saveIcon = document.getElementById('save-icon-' + postId)
    let unsaveIcon = document.getElementById('unsave-icon-' + postId)
    if (saveIcon.hidden) {
        unsaveIcon.hidden = true
        saveIcon.hidden = false
        return
    }
    unsaveIcon.hidden = false
    saveIcon.hidden = true
}

function changeCommentStateOfPostById(postId) {
    let postCommentFormBlock = document.getElementById('post-comment-form-' + postId)
    let commentsBlock = document.getElementById('post-' + postId + '-comments')
    if (postCommentFormBlock.hidden) {
        commentsBlock.classList.add('comments')
        commentsBlock.hidden = false
        postCommentFormBlock.hidden = false
        return
    }
    commentsBlock.classList.remove('comments')
    commentsBlock.hidden = true
    postCommentFormBlock.hidden = true
}

function fadeOutIn(elem) {
    var outInterval = setInterval(() => {
        elem.style.opacity = Number(elem.style.opacity) + 0.04
        if (elem.style.opacity >= 1) {
            clearInterval(outInterval)
            var inInterval = setInterval(() => {
            elem.style.opacity = Number(elem.style.opacity) - 0.03
            if (elem.style.opacity <= 0) {
                clearInterval(inInterval)
            }
            }, 30);
        }
    }, 10)
}

const postForm = document.getElementById('post-form')
postForm.addEventListener('submit', createPost)

function createPost(event) {
    event.preventDefault()
    let form = event.target
    let data = new FormData(form)
    const object = {}
    data.forEach((value, key) => {
        object[key] = value
    })
    let userId = object['user_id']
    let image = object['file']
    let description = object['description']
    sendPost(userId, image, description)
}

function sendPost(userId, image, description) {
    axios.post(BASE_URL + '/posts', {
        image,
        description,
        userId
    }, 
    {
        headers: {
            "Content-Type": "multipart/form-data"
        }
    })
    .then((response) => {
        let post = response.data
        let postElement = createPostElement(post)
        addPost(postElement)
        createListenersForPost(post)
    })
    .catch((error) => {
        alert(error)
    });
}

function createComment(event) {
    event.preventDefault()
    let form = event.target
    let data = new FormData(form)
    let comment = {}
    data.forEach((value, key) => {
        comment[key] = value
    })
    sendComment(comment)
}

function sendComment(comment) {
    axios.post(BASE_URL + '/comments', comment)
    .then((response) => {
        let data = response.data
        let commentElement = createCommentElement(data)
        addComment(comment.post_id, commentElement)
    })
    .catch((error) => {
        alert(error)
    })
}

function getPosts() {
    axios.get(BASE_URL + '/posts')
    .then((response) => {
        let data = response.data
        console.log(data)
        data.forEach((post) => {
            let postElement = createPostElement(post)
            addPost(postElement)
            createListenersForPost(post)
            let comments = post.comments
            comments.forEach((comment) => {
                let commentElement = createCommentElement(comment)
                addComment(post.id, commentElement)
            })
        })
    })
    .catch((error) => {
        alert(error)
    })
}