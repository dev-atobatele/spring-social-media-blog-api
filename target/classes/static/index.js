let token = null;

async function login() {
  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  const res = await fetch('/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username, password })
  });

  if (res.ok) {
    const data = await res.json();
    token = data.token; // assuming your login returns { token: "..." }
    alert('Login successful');
    localStorage.setItem('account_id', data.account_id);
  } else {
    alert('Login failed');
  }
}

async function register() {
  const newUsername = document.getElementById('newUsername').value;
  const newPassword = document.getElementById('newPassword').value;

  const res = await fetch('/register', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ username: newUsername, password: newPassword })
  });

  if (res.ok) {
    const data = await res.json();
    token = data.token; // assuming your login returns { token: "..." }
    alert('Registration successful');
  } else {
    alert('Registration failed');
  }
}

async function getMessages() {
  const res = await fetch('/messages', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  if (res.ok) {
    const messages = await res.json();
    const list = document.getElementById('messages');
    list.innerHTML = '';
    messages.forEach(msg => {
      const li = document.createElement('li');
      li.textContent = `${msg.message_id}: ${msg.message_text}`;
      list.appendChild(li);
    });
  } else {
    alert('Failed to load messages');
  }
}
async function postMessage() {
  const messageText = document.getElementById('messageText').value;

  const res = await fetch('/messages', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({posted_by: localStorage.getItem('account_id'), message_text: messageText })
  });

  if (res.ok) {
    alert('Message posted successfully');
    getMessages(); // Refresh the message list
  } else {
    alert('Failed to post message');
  }
}

document.getElementById('loginBtn').addEventListener('click', login);
document.getElementById('getMessagesBtn').addEventListener('click', getMessages);
document.getElementById('postMessageBtn').addEventListener('click', postMessage);