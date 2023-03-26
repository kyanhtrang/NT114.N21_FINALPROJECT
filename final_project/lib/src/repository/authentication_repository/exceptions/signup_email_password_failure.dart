class SignUpWithEmailAndPasswordFailure {
  final String message;

  const SignUpWithEmailAndPasswordFailure([this.message = "An unknown error occurred."]);

  factory SignUpWithEmailAndPasswordFailure.code(String code) {
    switch (code) {
      case 'user-not-found':
        return const SignUpWithEmailAndPasswordFailure("No user found for that email.");
      case 'wrong-password':
        return const SignUpWithEmailAndPasswordFailure("Wrong login credential.");
      default:
        return const SignUpWithEmailAndPasswordFailure("Can't Login at the moment.");
    }
  }
}
