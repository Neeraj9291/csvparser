# Leave Management System

A comprehensive online leave management system built with Spring Boot, featuring role-based access control, leave request management, and approval workflows.

## Features

### 🎯 Core Features
- **User Authentication & Authorization**: Secure login with role-based access control
- **Leave Request Management**: Submit, view, and manage leave requests
- **Approval Workflow**: Managers and admins can approve/reject leave requests
- **Role-Based Access**: Different interfaces for Employees, Managers, and Administrators
- **Dashboard**: Comprehensive dashboard with statistics and recent activities
- **Responsive Design**: Modern, mobile-friendly UI built with Bootstrap 5

### 👥 User Roles
- **Employee**: Submit leave requests, view personal requests, cancel pending requests
- **Manager**: Approve/reject employee leave requests, view team requests
- **Admin**: Full system access, user management, view all requests

### 📊 Leave Types
- Annual Leave
- Sick Leave
- Personal Leave
- Maternity Leave
- Paternity Leave
- Bereavement Leave
- Other

### 🔄 Request Status
- **Pending**: Awaiting approval
- **Approved**: Request approved
- **Rejected**: Request rejected
- **Cancelled**: Request cancelled by employee

## Technology Stack

- **Backend**: Spring Boot 3.4.8, Spring Security, Spring Data JPA
- **Database**: H2 Database (in-memory for development)
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Build Tool**: Maven
- **Java Version**: 17

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd csvparser
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Main Application: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:leavemanagement`
     - Username: `sa`
     - Password: (leave empty)

### Demo Credentials

The system comes with pre-configured demo users:

| Username | Password | Role | Description |
|----------|----------|------|-------------|
| admin | admin123 | ADMIN | Full system access |
| manager | manager123 | MANAGER | Can approve employee requests |
| employee | employee123 | EMPLOYEE | Can submit leave requests |
| john | john123 | EMPLOYEE | Sample employee |
| jane | jane123 | EMPLOYEE | Sample employee |

## Application Structure

```
src/main/java/com/neeraj/csvparser/
├── config/
│   ├── SecurityConfig.java          # Spring Security configuration
│   └── DataInitializer.java        # Sample data initialization
├── controller/
│   ├── MainController.java          # Home and dashboard controller
│   └── LeaveRequestController.java  # Leave request management
├── dto/
│   └── LeaveRequestDto.java        # Data transfer object
├── entity/
│   ├── User.java                   # User entity
│   └── LeaveRequest.java           # Leave request entity
├── repository/
│   ├── UserRepository.java         # User data access
│   └── LeaveRequestRepository.java # Leave request data access
├── service/
│   ├── UserService.java            # User business logic
│   └── LeaveRequestService.java    # Leave request business logic
└── LeaveManagementApplication.java  # Main application class
```

## Key Features Explained

### 🔐 Security
- Spring Security with form-based authentication
- Role-based authorization using `@PreAuthorize` annotations
- Password encryption using BCrypt
- Session management

### 📝 Leave Request Workflow
1. **Employee submits request**: Choose dates, leave type, and reason
2. **System validation**: Check for overlapping dates, required fields
3. **Manager/Admin review**: View request details and approve/reject
4. **Status update**: Request status updated with comments
5. **Notification**: Success/error messages displayed to users

### 🎨 User Interface
- **Responsive Design**: Works on desktop, tablet, and mobile
- **Modern UI**: Bootstrap 5 with custom gradients and animations
- **Intuitive Navigation**: Sidebar navigation with active state indicators
- **Real-time Feedback**: Success/error messages and form validation

### 📊 Dashboard Features
- **Statistics Cards**: Display approved, pending, and rejected request counts
- **Recent Requests**: Show latest leave requests with status badges
- **Pending Approvals**: For managers/admins to see requests needing approval
- **Quick Actions**: Direct links to submit new requests or view all requests

## Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique)
- `password` (Encrypted)
- `firstName`
- `lastName`
- `email` (Unique)
- `role` (EMPLOYEE, MANAGER, ADMIN)
- `enabled` (Boolean)

### Leave Requests Table
- `id` (Primary Key)
- `user_id` (Foreign Key to Users)
- `startDate`
- `endDate`
- `leaveType` (Enum)
- `reason`
- `status` (PENDING, APPROVED, REJECTED, CANCELLED)
- `createdAt`
- `updatedAt`
- `approvedBy` (Foreign Key to Users)
- `comments`

## API Endpoints

### Public Endpoints
- `GET /` - Home page
- `GET /login` - Login page
- `POST /login` - Login form submission

### Protected Endpoints
- `GET /dashboard` - User dashboard
- `GET /leave/request` - Leave request form
- `POST /leave/request` - Submit leave request
- `GET /leave/my-requests` - View personal requests
- `GET /leave/requests` - View all requests (managers/admins)
- `GET /leave/requests/{id}` - View request details
- `POST /leave/requests/{id}/approve` - Approve request
- `POST /leave/requests/{id}/reject` - Reject request
- `POST /leave/requests/{id}/cancel` - Cancel request

## Customization

### Adding New Leave Types
1. Add new enum value in `LeaveRequest.LeaveType`
2. Update the display name in the constructor
3. The UI will automatically pick up the new type

### Changing Database
To switch from H2 to MySQL/PostgreSQL:
1. Update `application.properties` with your database configuration
2. Add appropriate database dependency in `pom.xml`
3. Update `spring.jpa.database-platform`

### Styling
- Main styles are in `layout.html`
- Bootstrap 5 classes are used throughout
- Custom CSS for gradients and animations

## Development

### Running Tests
```bash
mvn test
```

### Building for Production
```bash
mvn clean package
```

### Database Migration
For production, consider using Flyway or Liquibase for database migrations.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

## Support

For issues and questions, please create an issue in the repository.

---

**Note**: This is a demo application with in-memory H2 database. For production use, configure a persistent database and implement additional security measures. 