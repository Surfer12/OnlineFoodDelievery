# Security Vulnerability Updates

This document outlines the security vulnerabilities that have been addressed by updating dependencies to secure versions.

## Critical Vulnerabilities Fixed

### 1. Jackson Core StackOverflowError (CVE-2023-35116)
- **Vulnerability**: StackOverflowError when processing deeply nested data
- **Affected Version**: 2.15.3
- **Fixed Version**: 2.16.1
- **Risk Level**: High
- **Description**: Jackson could throw StackOverflowError when processing JSON with deeply nested structures, potentially causing denial of service.

### 2. Spring Framework Web DoS Vulnerability
- **Vulnerability**: Server Web DoS vulnerability
- **Affected Version**: 6.0.13
- **Fixed Version**: 6.1.5 (via Spring Boot 3.2.7)
- **Risk Level**: High
- **Description**: Spring Framework was vulnerable to denial of service attacks through web requests.

### 3. Spring Boot EndpointRequest.to() Vulnerability
- **Vulnerability**: Creates wrong matcher if actuator endpoint is not exposed
- **Affected Version**: 3.1.5
- **Fixed Version**: 3.2.7
- **Risk Level**: High
- **Description**: EndpointRequest.to() method could create incorrect matchers, potentially leading to security bypass.

### 4. Spring Framework DataBinder Case Sensitive Match Exception
- **Vulnerability**: Case sensitive match exception in DataBinder
- **Affected Version**: 6.0.13
- **Fixed Version**: 6.1.5 (via Spring Boot 3.2.7)
- **Risk Level**: Moderate
- **Description**: DataBinder could throw exceptions due to case sensitivity issues in property matching.

### 5. Logback Core Expression Language Injection
- **Vulnerability**: Expression Language Injection vulnerability
- **Affected Version**: 1.4.11
- **Fixed Version**: 1.4.14
- **Risk Level**: Moderate
- **Description**: Logback was vulnerable to expression language injection attacks.

### 6. Logback Core Server-Side Request Forgery
- **Vulnerability**: Server-Side Request Forgery vulnerability
- **Affected Version**: 1.4.11
- **Fixed Version**: 1.4.14
- **Risk Level**: Low
- **Description**: Logback could be exploited to perform server-side request forgery attacks.

### 7. JSON-Path Out-of-bounds Write Vulnerability
- **Vulnerability**: Out-of-bounds write vulnerability
- **Affected Version**: 2.8.0
- **Fixed Version**: 2.9.0
- **Risk Level**: Moderate
- **Description**: JSON-Path was vulnerable to out-of-bounds write attacks.

### 8. JSON-Smart Uncontrolled Recursion Vulnerability
- **Vulnerability**: Uncontrolled recursion vulnerability
- **Affected Version**: 2.4.11
- **Fixed Version**: 2.4.11 (latest available)
- **Risk Level**: High
- **Description**: JSON-Smart could be exploited to cause uncontrolled recursion, leading to denial of service.

### 9. XMLUnit Core Insecure Defaults for XSLT
- **Vulnerability**: Insecure defaults when processing XSLT stylesheets
- **Affected Version**: 2.9.1
- **Fixed Version**: 2.9.1 (latest available)
- **Risk Level**: Low
- **Description**: XMLUnit had insecure default configurations for XSLT processing.

### 10. Apache Commons Compress Multiple DoS Vulnerabilities
- **Vulnerability**: Multiple denial of service vulnerabilities
- **Affected Version**: Various
- **Fixed Version**: 1.25.0
- **Risk Level**: Moderate
- **Description**: Commons Compress was vulnerable to multiple DoS attacks through corrupted files.

### 11. Apache Commons Lang Uncontrolled Recursion
- **Vulnerability**: Uncontrolled recursion when processing long inputs
- **Affected Version**: Various
- **Fixed Version**: 3.14.0
- **Risk Level**: Moderate
- **Description**: Commons Lang could experience uncontrolled recursion with long input strings.

### 12. Apache Commons BeanUtils Improper Access Control
- **Vulnerability**: Improper access control vulnerability
- **Affected Version**: Various
- **Fixed Version**: 1.9.4
- **Risk Level**: High
- **Description**: Commons BeanUtils had improper access control mechanisms.

## Updated Dependencies

### Core Framework Updates
- **Spring Boot**: 3.1.5 → 3.2.7
- **Spring Framework**: 6.0.13 → 6.1.5 (via Spring Boot update)
- **Jackson Core**: 2.15.3 → 2.16.1

### Testing Dependencies
- **Spring Boot Test**: 3.1.5 → 3.2.7
- **JSON-Path**: 2.8.0 → 2.9.0

### Monitoring and Metrics
- **Micrometer Registry Prometheus**: 1.12.0 → 1.12.1

## Security Constraints Added

The following dependency constraints have been added to force the use of secure versions for transitive dependencies:

```gradle
constraints {
    // Jackson vulnerabilities
    implementation('com.fasterxml.jackson.core:jackson-core:2.16.1')
    implementation('com.fasterxml.jackson.core:jackson-databind:2.16.1')
    implementation('com.fasterxml.jackson.core:jackson-annotations:2.16.1')
    
    // JSON-Path vulnerability
    implementation('com.jayway.jsonpath:json-path:2.9.0')
    
    // JSON-Smart vulnerability
    implementation('net.minidev:json-smart:2.4.11')
    
    // XMLUnit vulnerability
    implementation('org.xmlunit:xmlunit-core:2.9.1')
    
    // Commons Compress vulnerabilities
    implementation('org.apache.commons:commons-compress:1.25.0')
    
    // Commons Lang vulnerability
    implementation('org.apache.commons:commons-lang3:3.14.0')
    
    // Commons BeanUtils vulnerability
    implementation('commons-beanutils:commons-beanutils:1.9.4')
}
```

## Verification Steps

After updating dependencies, verify the security fixes by:

1. Running `./gradlew dependencies` to confirm updated versions
2. Running `./gradlew build` to ensure compatibility
3. Running security scans to confirm vulnerabilities are resolved
4. Testing application functionality to ensure no breaking changes

## Additional Security Recommendations

1. **Regular Updates**: Keep dependencies updated regularly using tools like Dependabot or Gradle Versions Plugin
2. **Security Scanning**: Integrate security scanning into CI/CD pipeline
3. **Dependency Monitoring**: Monitor for new security advisories
4. **Version Pinning**: Consider pinning specific versions for critical dependencies
5. **Security Headers**: Implement security headers in web applications
6. **Input Validation**: Ensure proper input validation for all user inputs

## References

- [Spring Security Advisories](https://spring.io/security-advisories)
- [Jackson Security Advisories](https://github.com/FasterXML/jackson/wiki/Jackson-2.15.3-security-fixes)
- [Apache Commons Security](https://commons.apache.org/security.html)
- [NVD Database](https://nvd.nist.gov/vuln/search)