ItemFinder – Lost & Found Web Application

Overview
ItemFinder is a self-hosted lost & found platform running on our VPS at itemfinder.net.
It allows users to report and browse lost & found items, featuring an interactive map, messaging system, and secure authentication.
The system is built with a Spring Boot backend, a PostgreSQL database, and a Thymeleaf-powered frontend.

Core Features:

🔹 User Management & Authentication
Registration & login system with hashed passwords stored securely
Access control: anyone can browse, but posting & messaging requires login
Planned OAuth login support (Google & other platforms)

📍 Lost & Found Item Management
Users can post lost or found items with descriptions, images, and locations
Locations are marked using an interactive map (Leaflet + OpenStreetMap)
Images are stored on the server, with file paths linked in the database

💬 Messaging System
Built-in direct messaging between users for lost & found coordination
Messages are stored in the database for tracking conversations
Messaging system improvements in progress

🌍 Multilingual Support
The website is available in two languages, managed via Spring messages.properties
Language adapts based on user preferences

📱 Mobile App Integration
A mobile client is under development, integrating with the web platform

Planned Improvements 🚀
UI improvements for a more intuitive user experience
Extended messaging functionality (attachments, better notifications)
OAuth login options (Google & other platforms)

Live Demo
🔗 Visit the live version at itemfinder.net
