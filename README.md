# Quickscribe
<img width="1536" height="1024" alt="ChatGPT Image Dec 19, 2025, 04_25_18 PM" src="https://github.com/user-attachments/assets/97dd366a-34ff-4d18-8a05-cfc18cb10e97" />

> Asynchronous audio transcription service using a web API, a durable queue, and isolated Whisper workers.


## Overview
---


`quickscribe.net` is a system for asynchronously transcribing uploaded audio files.
The system follows a **web + queue + worker** architecture to decouple request handling
from long-running transcription jobs.

Clients submit jobs via an HTTP API, upload audio to object storage, and poll for results.
Workers consume jobs from a queue, run Whisper, and persist transcription artifacts.

---




## High-Level Architecture
<img width="777" height="337" alt="Cloud Architecture Diagram drawio (3)" src="https://github.com/user-attachments/assets/4035e1a6-fb4e-4934-859b-8424b4a266a1" />

**Components**
- API Gateway — public HTTP entry point
- Spring Boot API (ECS) — job creation, validation, lifecycle management
- S3 — storage for source audio and transcription outputs
- SQS (+ DLQ) — durable job queue with retry semantics
- ECS Workers (Python / Whisper) — transcription compute
- DynamoDB — authoritative job state store

---



## Transcription Flow

<img width="632" height="688" alt="Transcription Flow drawio" src="https://github.com/user-attachments/assets/cb30abef-9022-4e29-b115-eb1547c83546" />


1. Client submits a transcription job
2. API creates a job record and returns a presigned upload URL
3. Client uploads audio to object storage
4. API enqueues the job to the worker queue
5. Worker consumes the job and runs Whisper
6. Transcription artifacts are stored and job state updated
7. Client polls API for status and results

---


## Entity Relationship Diagram

<img width="768" height="732" alt="ERD drawio" src="https://github.com/user-attachments/assets/76779240-b4f1-4330-9b75-c133f74e5174" />

**Primary Entities**
- Job
- Artifact
- WebhookSubscription

DynamoDB is used for job state due to predictable access patterns and low-latency access.

---

## Design Principles

- Asynchronous job processing
- Explicit queue-based backpressure
- At-least-once delivery with idempotent workers
- Compute isolated from HTTP request paths
- Horizontal scaling via queue depth
- Clear separation between API and worker responsibilities

---

## Technology Stack

**Backend**
- Java 17
- Spring Boot (API)
- Python + Whisper (Workers)

**Infrastructure**
- API Gateway
- S3
- SQS + DLQ
- DynamoDB
- ECS (Fargate / EC2)


---

## Upcoming Features
- Websocket connection to workers for realtime communication
