# Tic-Tac-Toe-Competitive-master

Welcome to Tic Tac Toe Competitive, a game that nobody asked for but everyone will secretly play at 2 AM.
Built with Java, HTML, and vanilla JavaScript (yes, we like suffering), this project shows how to glue together backend and frontend to make little X’s and O’s fight each other.

Backend: Spring Boot (because why not let Papa Spring do the heavy lifting?)

Frontend: HTML, CSS, and vanilla JS (spaghetti edition 🍝)

## Frontend Architecture

Let’s be honest: it’s a pile of HTML, CSS, and JS files. You’ll have to dig through them like an archaeologist to understand the structure.
👉 Start your expedition with game.html.

## Backend Architecture

First impression: “SO MANY FOLDERS, SO MANY FILES — what is this sorcery?”
Don’t worry. Let’s unpack it step by step.

## Model

Where the data lives. Or, as I call it, “the dumb part of the app.”

AbstractEntity: Base class with id and version. Mostly there to make JPA happy.

Game: Represents an actual Tic Tac Toe game. Holds board, players, status, and logic to manage the game.

Player: Represents a player (name, symbol, etc.).

👉 Philosophy: models should be as dumb as possible. No fancy DB tricks here.

## Controller

Data is good. Data you can access is better. Controllers are the friendly bouncers between frontend and backend.
They:

Handle HTTP requests

Call services

Return responses

So yeah, an API.

💡 Pro tip: Don’t just expose /game. Prefix it with /api or /v1 or whatever. Even for toy projects, it saves headaches later.

GameController: Handles creating games, making moves, and checking status.

## Service

If controllers are the fun, social butterflies, services are the introverts who actually get work done.
They contain the business logic — all the rules that make the game function.

GameService: Creates games, makes moves, checks winners, keeps everything from catching fire.

## Repository

Data is love, data is life. Here’s where it gets stored.

Why write raw SQL when JPA can cheese handle it for you?

id: obvious.

version: used for optimistic locking (aka: stopping two players from breaking the game when they click at the same time).

## Scheduling

What if:

A player leaves mid-game?

You want to spam the leaderboard?

Or you just want ice cream at 3 PM every day? 🍦

That’s what scheduling is for. Papa Spring Boot’s got your back.
Check out BotMatchesScheduler for an example.

## Session

We’ve got games, players, boards… but wait. Who’s who?

Enter sessions:

Each user gets a unique session ID (stored in a cookie).

Spring magically maps it back to the right player.

Boom. Problem solved.

## Resource

Controllers shouldn’t return models directly. Why?

Security: models might contain stuff you don’t want public.

Serialization: circular references = 💥.

Decoupling: models and APIs shouldn’t break each other.

So instead we use resources — lightweight objects made for API responses.

## Messaging

Player 1 moves. How does Player 2 know without spamming the server every 2 seconds?

WebSockets to the rescue! 🎺
But raw WebSocket code is… nope.

So we use STOMP on top. It gives us neat topics like /ws/game-topic.

Clients subscribe to /ws/game-topic/{game-id}.

Server shouts updates.

Clients listen like good little minions.

## Final Words

That’s the grand tour. 🎉

Go forth, explore the codebase, and have fun breaking (or winning) Tic Tac Toe.
I’ll probably update this README again once I’ve had more coffee ☕.