# 💬 Simple Chat Platform (Spring Boot Backend)

## Overview

A lightweight real-time chat backend built with **Spring Boot (Java 17)**. The application uses asynchronous message processing via a `BlockingQueue` and **WebSockets (STOMP)** for real-time communication. No database is used, making the solution simple, fast, and focused on messaging architecture and concurrency concepts.

---

## Features

* Join chat with username (no authentication)
* Send messages via REST API
* Receive messages in real time through WebSockets
* Asynchronous message processing using the Producer-Consumer pattern
* In-memory active user tracking
* Clean layered architecture

---

## Architecture

### High-Level Flow

```text
REST API
   ↓
Controller
   ↓
Service Layer
   ↓
Message Producer
   ↓
BlockingQueue
   ↓
Async Consumer
   ↓
WebSocket Broadcaster
   ↓
Connected Clients
```

### WebSocket Flow

```text
Client
   ↓
/ws-chat
   ↓
STOMP Broker
   ↓
/topic/chat
   ↓
All Connected Clients
```

---

## Design Decisions

### BlockingQueue

#### Benefits

* Thread-safe
* Built-in concurrency support
* Decouples message producers from consumers
* Simple asynchronous processing model

#### Trade-Off

* No persistence across application restarts

---

### Asynchronous Processing

#### Benefits

* Non-blocking REST endpoints
* Producer-Consumer architecture
* Separation between API and message broadcasting
* Better responsiveness under load

---

### WebSockets

#### Benefits

* Real-time communication
* Efficient message delivery
* No polling required
* STOMP protocol support

---

### Layered Architecture

```text
Controller
   ↓
Service
   ↓
Messaging
   ↓
WebSocket
```

#### Benefits

* Separation of concerns
* Maintainability
* Testability

---

### No Database

#### Benefits

* Simpler architecture
* Focus on messaging and concurrency concepts
* Reduced infrastructure requirements

---

## Project Structure

```text
com.jse.chat
├── controller
│   └── ChatController
│
├── service
│   ├── ChatService
│   └── ChatSessionService
│
├── messaging
│   ├── MessageProducer
│   ├── MessageConsumer
│   └── MessageQueueConfig
│
├── websocket
│   ├── MessageBroadcaster
│   └── WebSocketConfig
│
├── domain
│   └── ChatMessage
│
├── dto
│   ├── SendMessageRequest
│   └── JoinRequest
│
├── config
│   ├── TimeConfig
│   ├── ChatQueueProperties
│   ├── ChatWebSocketProperties
│   └── CorsProperties
│
└── exception
    └── GlobalExceptionHandler
```

---

## Concurrency Model

```text
Producer
   ↓
Bounded BlockingQueue
   ↓
Consumer Thread
   ↓
WebSocket Broadcast
   ↓
Connected Clients
```

The Producer publishes messages into a thread-safe queue while a dedicated Consumer thread processes and broadcasts messages asynchronously.

---

## Running the Application

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

### Application URL

```text
http://localhost:8080
```

---

## REST APIs

### Join Chat

**Endpoint**

```http
POST /api/chat/join
```

**Request Body**

```json
{
  "username": "john"
}
```

---

### Send Message

**Endpoint**

```http
POST /api/chat/send
```

**Request Body**

```json
{
  "username": "john",
  "content": "Hello World"
}
```

---

## WebSocket Configuration

### Endpoint

```text
ws://localhost:8080/ws-chat
```

### Topic

```text
/topic/chat
```

Clients subscribe to `/topic/chat` to receive real-time messages.

---

## Message Processing Flow

```text
User
   ↓
REST API
   ↓
Validation
   ↓
Queue
   ↓
Consumer Thread
   ↓
WebSocket Broadcast
```

---

## Testing Strategy

### Controller Tests

* MockMvc

### Service Tests

* Mockito

### Messaging Tests

* Queue behavior
* Producer/Consumer validation

### WebSocket Tests

* MessageBroadcaster testing using mocked SimpMessagingTemplate

### Session Management Tests

* User join/leave scenarios
* Active user tracking

---

## Tools & Frameworks

* Java 17
* Spring Boot 3.x
* Spring WebSocket
* STOMP
* Lombok
* JUnit 5
* Mockito
* MockMvc

---

## Key Learning Outcomes

* Producer-Consumer Pattern
* Concurrent Programming
* WebSocket Communication
* Thread-Safe Data Structures
* Clean Architecture
* Spring Boot Testing
* Asynchronous Processing

---

## System Flow Diagram

```text
Client
  │
  ▼
REST API
  │
  ▼
ChatController
  │
  ▼
ChatService
  │
  ▼
MessageProducer
  │
  ▼
BlockingQueue
  │
  ▼
MessageConsumer
  │
  ▼
MessageBroadcaster
  │
  ▼
WebSocket Topic (/topic/chat)
  │
  ▼
Connected Clients
```

---

## Trade-Offs

### Advantages

* Simple and lightweight architecture
* Real-time communication
* No database dependency
* Demonstrates concurrency and asynchronous processing
* Easy to understand, maintain, and test

### Limitations

* Messages are lost after application restart
* Single-instance deployment only
* No authentication or authorization
* No horizontal scaling without additional infrastructure

```
```
