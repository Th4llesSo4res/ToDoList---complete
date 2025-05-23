const token = localStorage.getItem('token');
let tasks = [];

async function loadTasks() {
  const res = await fetch('http://localhost:8080/tasks', {
    headers: { 'Authorization': `Bearer ${token}` }
  });

  if (!res.ok) return alert('Erro ao carregar tarefas.');

  tasks = await res.json();
  renderTasks(tasks);
  showWelcomeMessage();
}

function renderTasks(lista) {
  const container = document.getElementById('taskList');
  container.innerHTML = '';

  lista.forEach(task => {
    const div = document.createElement('div');
    div.className = 'task-item';
    div.innerHTML = `
      <div>
        <p><strong>${task.title}</strong> ${task.completed ? '✅' : '❌'}</p>
        <p>${task.description || ''}</p>
        <p><small>${task.date || ''}</small></p>
      </div>
      <div class="task-actions">
        <button onclick="completeTask(${task.id})">Concluir</button>
        <button onclick="showEditForm(${task.id})">Editar</button>
        <button class="delete" onclick="deleteTask(${task.id})">Excluir</button>
      </div>
    `;
    container.appendChild(div);
  });
}

async function addTask() {
  const title = document.getElementById('newTask').value.trim();
  const description = document.getElementById('newDescription').value.trim();
  const date = document.getElementById('newDate').value;

  if (!title) return alert('Digite um título válido.');

  await fetch('http://localhost:8080/tasks', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({ title, description, date })
  });

  document.getElementById('newTask').value = '';
  document.getElementById('newDescription').value = '';
  document.getElementById('newDate').value = '';
  loadTasks();
}

async function completeTask(id) {
  await fetch(`http://localhost:8080/tasks/${id}/complete`, {
    method: 'PUT',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  loadTasks();
}

async function deleteTask(id) {
  await fetch(`http://localhost:8080/tasks/${id}`, {
    method: 'DELETE',
    headers: { 'Authorization': `Bearer ${token}` }
  });
  loadTasks();
}

function showEditForm(id) {
  const task = tasks.find(t => t.id === id);
  const title = prompt('Novo título:', task.title);
  const description = prompt('Nova descrição:', task.description || '');
  const date = prompt('Nova data (yyyy-mm-dd):', task.date || '');

  if (title !== null) updateTask(id, title, description, date);
}

async function updateTask(id, title, description, date) {
  const res = await fetch(`http://localhost:8080/tasks/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({ title, description, date })
  });

  if (!res.ok) return alert('Erro ao editar tarefa.');
  loadTasks();
}

function filterTasks() {
  const filter = document.getElementById('filter').value;
  let filtered = tasks;

  if (filter === 'completed') filtered = tasks.filter(t => t.completed);
  else if (filter === 'pending') filtered = tasks.filter(t => !t.completed);

  renderTasks(filtered);
}

function sortTasks() {
  const sort = document.getElementById('sort').value;
  const sorted = [...tasks].sort((a, b) => {
    if (sort === 'asc') return a.title.localeCompare(b.title);
    return b.title.localeCompare(a.title);
  });

  renderTasks(sorted);
}

function logout() {
  localStorage.removeItem('token');
  window.location.href = 'index.html';
}

function showWelcomeMessage() {
  const email = JSON.parse(atob(token.split('.')[1])).sub;
  document.getElementById('welcome').innerText = `Bem-vindo! O que vamos planejar hoje?`;
}

loadTasks();
