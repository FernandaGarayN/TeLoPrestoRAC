let timeout;
const sessionTimeout = 10 * 60 * 1000; // 10 minutes
const warningTimeout = 7 * 60 * 1000; // 7 minutes

function startTimer() {
    timeout = setTimeout(showWarning, sessionTimeout - warningTimeout);
}

function resetTimer() {
    clearTimeout(timeout);
    startTimer();
}

function showWarning() {
    alert('Tu sesión está a punto de caducar por inactividad. Por favor, realiza alguna acción para mantener la sesión activa.');
}

// Start the session timeout timer when the page loads
window.onload = startTimer;

// Reset the session timeout timer whenever the user performs an action
window.onmousemove = resetTimer;
window.onmousedown = resetTimer;
window.onclick = resetTimer;
window.onscroll = resetTimer;
window.onkeypress = resetTimer;