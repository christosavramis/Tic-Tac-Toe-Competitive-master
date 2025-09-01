window.startSingleplayerGame = async function() {
    const response = await fetch('http://localhost:8080/game/start', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' }
    });
    if (!response.ok) throw new Error('Failed to start the game');
    return await response.json();
};

window.sendStarterChoice = async function(starterKey) {
    const response = await fetch('http://localhost:8080/game/starter', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ starter: starterKey })
    });
    if (!response.ok) throw new Error('Failed to set starter');
    return await response.json();
};

window.getProfile = async function(playerId) {
    const response = await fetch(`http://localhost:8080/game/profile/${playerId}`);
    if (!response.ok) throw new Error('Failed to fetch profile');
    return await response.json();
};