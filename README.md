# Ticket-Booking-System
This is a ticket booking sytem using GUI and TCP IP
A user can register and login to book tickets
A user can book atmost 4 tickets. Even if he logs out an logs back in he can only book 4 tickets.
Race condition is managed to avoid two users booking the same seat.
The Database server is multithreaded to listen to multiple client requests.
