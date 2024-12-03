# Reusable Android Components

## 📱 Repository Overview

A comprehensive collection of reusable Android components designed to accelerate development and maintain code consistency across projects.

## 🧩 Modules Overview

### 1. Loaders Module
- Multiple custom loaders created using Jetpack Compose
- Compatible with Compose UI and XML layouts
- Easy integration and customization

#### Features
- Various loading animation styles
- Cross-platform compatibility
- Lightweight implementation

### 2. Generic RecyclerView Adapter
- Universal RecyclerView adapter
- Reduces boilerplate code
- Supports complex list rendering
- Type-safe implementations

### 3. Permissions Handler
- `PermissionHelper` class
- Comprehensive permission management
- Supports:
  - Single permission requests
  - Multiple permission requests
  - Granular permission handling

### 4. Retrofit Implementation Skeleton
- MVVM Architecture
- Fake interceptor for testing
- Robust network layer implementation
- Easy to integrate into new projects

### 5. Payment Integration Module
#### Supported Payment Gateways
- Stripe
- Razorpay

##### Stripe Features
- Card management
  - Add card
  - Edit card
  - Delete card
- Test environment support

##### Razorpay Features
- Payment processing
- Test mode integration

### 6. ML Kit Implementation
#### Supported Recognition Types
- Face recognition
- Card number detection
- Vehicle number plate recognition

### 7. Media Picker Wrapper
- Simplified media selection
- Support for:
  - Single image/video selection
  - Multiple image/video selection
- Abstraction over Android media picker

### 8. ViewPager with Tabs
- Tabbed interface implementation
- Tabs positioned at top
- Smooth navigation between fragments

## 🚀 Quick Start

## 🛠 Usage Examples

### Loaders Module
```kotlin
// Compose UI
@Composable
fun LoaderExample() {
    CircularLoader() // Simple circular loader
}
```

### Permissions Module
```kotlin
// Request single permission
PermissionHelper.requestPermission(
    context, 
    Manifest.permission.CAMERA
) { isGranted ->
    // Handle permission result
}
```

### Retrofit Module
```kotlin
// ViewModel example
class ExampleViewModel(private val repository: Repository) : ViewModel() {
    fun fetchData() {
        viewModelScope.launch {
            // Fetch data using repository
        }
    }
}
```

## 🔒 Security Considerations

- Secure implementation of sensitive modules
- Regular security audits
- Follows Android security best practices

## 📦 Requirements

- Android Studio Arctic Fox or later
- Kotlin 1.7+
- AndroidX libraries
- Jetpack Compose

## 🤝 Contributing

1. Fork the repository
2. Create feature branch
3. Implement changes
4. Write comprehensive tests
5. Submit pull request

### Contribution Guidelines
- Follow existing code style
- Add unit tests
- Update documentation
- Ensure backward compatibility

## 📄 License
[Specify your license]

## 👥 Maintained By
[Sagar Pahwa]

## 📞 Support
For issues or feature requests, please open a GitHub issue.
