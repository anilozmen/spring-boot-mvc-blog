# Simple Spring Blog Project

Simple blog with Spring MVC.

## Features

- **Admin Role**: As an admin, you have the power to edit, remove, and delete any posts and comments. You can also create new posts and comments.

- **User Role**: Users can create new posts and comments as well. However, they can only edit or delete their own posts and comments.

- **User Authentication**: Secure your blog with Spring Security, providing both admin and user roles for different levels of access.

- **Database Persistence**: Using Spring Data JPA, your blog content will be stored in a PostgreSQL database, ensuring data durability and reliability.

- **Soft Delete**: Benefit from a soft delete feature, allowing for content removal without permanent data loss.

- **Rich Text Editing**: We've integrated CKEditor, a powerful and user-friendly rich text editor, allowing you to create visually appealing and well-formatted blog posts.

- **Friendly URLs**: Each blog post generates a unique slug, making your URLs clean and search engine friendly.

- **Validation**: Input validation is taken care of, ensuring that your blog content remains consistent and error-free.

- **Template**: The project utilizes the [Clean Blog](https://startbootstrap.com/theme/clean-blog) theme from Start Bootstrap to provide a clean and attractive user interface.

## User and Admin Login Information

- **Admin**: `admin@admin.com` / `password`
- **User**: `user@user.com` / `password`


## Installation

Follow these steps to get the project up and running:

1. **Clone the Repository**
```bash
git clone https://github.com/anilozmen/spring-boot-mvc-blog.git
cd spring-boot-mvc-blog
```

2. **Database Configuration**
   Make sure you have PostgreSQL installed and create a new database. Update the `application.yml` file with your database credentials.

3. **Run the Application**
```bash
mvn spring-boot:run
```