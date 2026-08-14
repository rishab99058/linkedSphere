import 'dart:math' as math;
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:mobile/core/costants.dart';
import 'package:mobile/features/onboarding/screens/onboarding.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({super.key});

  @override
  State<SplashScreen> createState() => _SplashScreenState();
}

class _SplashScreenState extends State<SplashScreen>
    with TickerProviderStateMixin {
  late final AnimationController _mainController;
  late final AnimationController _loopController;

  late final Animation<double> _sphereFade;
  late final Animation<double> _sphereScale;
  late final Animation<double> _textFade;
  late final Animation<Offset> _textSlide;
  late final Animation<double> _taglineFade;
  late final Animation<double> _ringReveal;

  @override
  void initState() {
    super.initState();

    SystemChrome.setSystemUIOverlayStyle(
      const SystemUiOverlayStyle(
        statusBarColor: Colors.transparent,
        statusBarIconBrightness: Brightness.light,
        statusBarBrightness: Brightness.dark,
      ),
    );

    // Main entrance animation
    _mainController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 1800),
    );

    // Looping animation for sphere rotation, rings, shimmer
    _loopController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 3000),
    )..repeat();

    _sphereFade = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _mainController,
        curve: const Interval(0.0, 0.35, curve: Curves.easeOut),
      ),
    );

    _sphereScale = Tween<double>(begin: 0.5, end: 1.0).animate(
      CurvedAnimation(
        parent: _mainController,
        curve: const Interval(0.0, 0.45, curve: Curves.elasticOut),
      ),
    );

    _ringReveal = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _mainController,
        curve: const Interval(0.15, 0.55, curve: Curves.easeOut),
      ),
    );

    _textFade = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _mainController,
        curve: const Interval(0.35, 0.65, curve: Curves.easeOut),
      ),
    );

    _textSlide = Tween<Offset>(begin: const Offset(0, 0.4), end: Offset.zero)
        .animate(
          CurvedAnimation(
            parent: _mainController,
            curve: const Interval(0.35, 0.65, curve: Curves.easeOutCubic),
          ),
        );

    _taglineFade = Tween<double>(begin: 0.0, end: 1.0).animate(
      CurvedAnimation(
        parent: _mainController,
        curve: const Interval(0.55, 0.85, curve: Curves.easeOut),
      ),
    );

    _mainController.forward();

    // Navigate to HomeScreen after 3 seconds
    Future.delayed(const Duration(seconds: 3), _goToHome);
  }

  void _goToHome() {
    if (!mounted) return;
    Navigator.of(context).pushReplacement(
      MaterialPageRoute(builder: (_) => const OnBoardingScreen()),
    );
  }

  @override
  void dispose() {
    _mainController.dispose();
    _loopController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    final shortestSide = size.shortestSide;
    final isSmallScreen = shortestSide < 360;

    final sphereSize = shortestSide * (isSmallScreen ? 0.28 : 0.3);
    final titleSize = shortestSide * (isSmallScreen ? 0.065 : 0.075);
    final taglineSize = shortestSide * (isSmallScreen ? 0.03 : 0.034);
    final ringMaxRadius = shortestSide * 0.3;

    return Scaffold(
      body: Container(
        width: double.infinity,
        height: double.infinity,
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [
              Color(0xFF0A1628),
              Color(0xFF0F2847),
              Color(0xFF0B1D3A),
              Color(0xFF020817),
            ],
            stops: [0.0, 0.35, 0.65, 1.0],
          ),
        ),
        child: Stack(
          children: [
            // Ambient floating orbs
            ...List.generate(5, (i) => _buildFloatingOrb(size, i)),

            // Pulsing concentric rings
            Center(
              child: AnimatedBuilder(
                animation: Listenable.merge([_loopController, _mainController]),
                builder: (context, _) {
                  return CustomPaint(
                    size: Size(ringMaxRadius * 2, ringMaxRadius * 2),
                    painter: _PulseRingPainter(
                      loopProgress: _loopController.value,
                      revealProgress: _ringReveal.value,
                      maxRadius: ringMaxRadius,
                    ),
                  );
                },
              ),
            ),

            // Main content
            Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Spacer(flex: 3),

                  // Animated sphere shape
                  AnimatedBuilder(
                    animation: Listenable.merge([
                      _mainController,
                      _loopController,
                    ]),
                    builder: (context, _) {
                      return Opacity(
                        opacity: _sphereFade.value,
                        child: Transform.scale(
                          scale: _sphereScale.value,
                          child: SizedBox(
                            width: sphereSize,
                            height: sphereSize,
                            child: CustomPaint(
                              painter: _SpherePainter(
                                rotationProgress: _loopController.value,
                              ),
                            ),
                          ),
                        ),
                      );
                    },
                  ),

                  SizedBox(height: shortestSide * 0.06),

                  // App name with shimmer
                  AnimatedBuilder(
                    animation: Listenable.merge([
                      _mainController,
                      _loopController,
                    ]),
                    builder: (context, _) {
                      return SlideTransition(
                        position: _textSlide,
                        child: Opacity(
                          opacity: _textFade.value,
                          child: ShaderMask(
                            shaderCallback: (bounds) {
                              final shimmerPos =
                                  (_loopController.value * 1.6 - 0.3).clamp(
                                    0.0,
                                    1.0,
                                  );
                              return LinearGradient(
                                colors: const [
                                  Colors.white,
                                  Color(0xFF60A5FA),
                                  Color(0xFFA78BFA),
                                  Colors.white,
                                ],
                                stops: [
                                  (shimmerPos - 0.3).clamp(0.0, 1.0),
                                  (shimmerPos - 0.1).clamp(0.0, 1.0),
                                  (shimmerPos + 0.1).clamp(0.0, 1.0),
                                  (shimmerPos + 0.3).clamp(0.0, 1.0),
                                ],
                                begin: Alignment.centerLeft,
                                end: Alignment.centerRight,
                              ).createShader(bounds);
                            },
                            child: Text(
                              AppConstants.appName,
                              style: TextStyle(
                                fontSize: titleSize,
                                fontWeight: FontWeight.w700,
                                color: Colors.white,
                                letterSpacing: 1.8,
                              ),
                            ),
                          ),
                        ),
                      );
                    },
                  ),

                  SizedBox(height: shortestSide * 0.018),

                  // Tagline
                  FadeTransition(
                    opacity: _taglineFade,
                    child: Text(
                      AppConstants.appSubtitle,
                      style: TextStyle(
                        fontSize: taglineSize,
                        color: const Color(0xFF64748B),
                        fontWeight: FontWeight.w400,
                        letterSpacing: 2.5,
                      ),
                    ),
                  ),

                  const Spacer(flex: 2),

                  // Bottom loading dots
                  FadeTransition(
                    opacity: _taglineFade,
                    child: _buildLoadingDots(shortestSide),
                  ),

                  SizedBox(height: shortestSide * 0.1),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  // --- Helper widgets ---

  Widget _buildLoadingDots(double shortestSide) {
    return SizedBox(
      width: shortestSide * 0.15,
      height: shortestSide * 0.04,
      child: AnimatedBuilder(
        animation: _loopController,
        builder: (context, _) {
          return CustomPaint(
            painter: _LoadingDotsPainter(progress: _loopController.value),
          );
        },
      ),
    );
  }

  Widget _buildFloatingOrb(Size screenSize, int index) {
    final rng = math.Random(index * 37 + 7);
    final x = rng.nextDouble() * screenSize.width;
    final y = rng.nextDouble() * screenSize.height;
    final orbSize = 80.0 + rng.nextDouble() * 160;
    final isBlue = index.isEven;

    return Positioned(
      left: x - orbSize / 2,
      top: y - orbSize / 2,
      child: AnimatedBuilder(
        animation: _loopController,
        builder: (context, _) {
          final drift =
              math.sin(_loopController.value * 2 * math.pi + index * 1.2) * 10;
          return Transform.translate(
            offset: Offset(drift, drift * 0.7),
            child: Container(
              width: orbSize,
              height: orbSize,
              decoration: BoxDecoration(
                shape: BoxShape.circle,
                gradient: RadialGradient(
                  colors: [
                    (isBlue ? const Color(0xFF3B82F6) : const Color(0xFF8B5CF6))
                        .withValues(alpha: 0.05),
                    (isBlue ? const Color(0xFF3B82F6) : const Color(0xFF8B5CF6))
                        .withValues(alpha: 0.0),
                  ],
                ),
              ),
            ),
          );
        },
      ),
    );
  }
}

// ============================================================
// Custom Painters
// ============================================================

/// Draws a stylized wireframe sphere with rotating orbital rings
class _SpherePainter extends CustomPainter {
  final double rotationProgress;
  _SpherePainter({required this.rotationProgress});

  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);
    final radius = size.width / 2;
    final angle = rotationProgress * 2 * math.pi;

    // Outer glow
    canvas.drawCircle(
      center,
      radius * 0.9,
      Paint()
        ..color = const Color(0xFF3B82F6).withValues(alpha: 0.12)
        ..maskFilter = const MaskFilter.blur(BlurStyle.normal, 25),
    );

    // Main sphere outline (gradient stroke)
    canvas.drawCircle(
      center,
      radius * 0.85,
      Paint()
        ..shader = const LinearGradient(
          colors: [Color(0xFF3B82F6), Color(0xFF8B5CF6), Color(0xFF06B6D4)],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ).createShader(Rect.fromCircle(center: center, radius: radius))
        ..style = PaintingStyle.stroke
        ..strokeWidth = 2.0,
    );

    // Inner glow fill
    canvas.drawCircle(
      center,
      radius * 0.85,
      Paint()
        ..shader = RadialGradient(
          colors: [
            const Color(0xFF3B82F6).withValues(alpha: 0.08),
            const Color(0xFF8B5CF6).withValues(alpha: 0.03),
            Colors.transparent,
          ],
          stops: const [0.0, 0.5, 1.0],
        ).createShader(Rect.fromCircle(center: center, radius: radius * 0.85)),
    );

    // Horizontal latitude lines
    for (int i = 1; i <= 4; i++) {
      final t = i / 5.0;
      final y = center.dy - radius * 0.85 * math.cos(t * math.pi);
      final lineRadius = radius * 0.85 * math.sin(t * math.pi);
      if (lineRadius > 0) {
        canvas.drawOval(
          Rect.fromCenter(
            center: Offset(center.dx, y),
            width: lineRadius * 2,
            height: lineRadius * 0.3,
          ),
          Paint()
            ..color = const Color(0xFF3B82F6).withValues(alpha: 0.15 + t * 0.1)
            ..style = PaintingStyle.stroke
            ..strokeWidth = 0.8,
        );
      }
    }

    // Orbital ring 1 (rotating)
    _drawOrbitalRing(
      canvas,
      center,
      radius,
      angle,
      orbitWidth: 1.7,
      orbitHeight: 0.5,
      strokeWidth: 1.5,
      color: const Color(0xFF60A5FA).withValues(alpha: 0.4),
      dotAngle: angle * 1.5,
      dotRadiusX: 0.85,
      dotRadiusY: 0.25,
      dotColor: const Color(0xFF60A5FA),
      dotSize: 3.5,
    );

    // Orbital ring 2 (counter-rotating, tilted)
    _drawOrbitalRing(
      canvas,
      center,
      radius,
      -angle * 0.7 + math.pi / 3,
      orbitWidth: 1.5,
      orbitHeight: 0.6,
      strokeWidth: 1.2,
      color: const Color(0xFFA78BFA).withValues(alpha: 0.3),
      dotAngle: -angle * 1.2,
      dotRadiusX: 0.75,
      dotRadiusY: 0.3,
      dotColor: const Color(0xFFA78BFA),
      dotSize: 3.0,
    );

    // Center bright point
    canvas.drawCircle(
      center,
      2.5,
      Paint()
        ..color = Colors.white.withValues(alpha: 0.6)
        ..maskFilter = const MaskFilter.blur(BlurStyle.normal, 4),
    );
  }

  void _drawOrbitalRing(
    Canvas canvas,
    Offset center,
    double radius,
    double rotation, {
    required double orbitWidth,
    required double orbitHeight,
    required double strokeWidth,
    required Color color,
    required double dotAngle,
    required double dotRadiusX,
    required double dotRadiusY,
    required Color dotColor,
    required double dotSize,
  }) {
    canvas.save();
    canvas.translate(center.dx, center.dy);
    canvas.rotate(rotation);
    canvas.translate(-center.dx, -center.dy);

    canvas.drawOval(
      Rect.fromCenter(
        center: center,
        width: radius * orbitWidth,
        height: radius * orbitHeight,
      ),
      Paint()
        ..color = color
        ..style = PaintingStyle.stroke
        ..strokeWidth = strokeWidth,
    );

    final dotX = center.dx + radius * dotRadiusX * math.cos(dotAngle);
    final dotY = center.dy + radius * dotRadiusY * math.sin(dotAngle);
    canvas.drawCircle(Offset(dotX, dotY), dotSize, Paint()..color = dotColor);
    canvas.drawCircle(
      Offset(dotX, dotY),
      dotSize + 1,
      Paint()
        ..color = dotColor.withValues(alpha: 0.4)
        ..maskFilter = const MaskFilter.blur(BlurStyle.normal, 6),
    );

    canvas.restore();
  }

  @override
  bool shouldRepaint(_SpherePainter oldDelegate) => true;
}

/// Pulsing concentric rings behind the sphere
class _PulseRingPainter extends CustomPainter {
  final double loopProgress;
  final double revealProgress;
  final double maxRadius;

  _PulseRingPainter({
    required this.loopProgress,
    required this.revealProgress,
    required this.maxRadius,
  });

  @override
  void paint(Canvas canvas, Size size) {
    final center = Offset(size.width / 2, size.height / 2);
    for (int i = 0; i < 3; i++) {
      final p = ((loopProgress + i * 0.33) % 1.0);
      final r = maxRadius * 0.35 + maxRadius * 0.65 * p;
      final a = (1.0 - p) * 0.12 * revealProgress;
      if (a <= 0) continue;
      canvas.drawCircle(
        center,
        r,
        Paint()
          ..color = const Color(0xFF3B82F6).withValues(alpha: a)
          ..style = PaintingStyle.stroke
          ..strokeWidth = 1.2,
      );
    }
  }

  @override
  bool shouldRepaint(_PulseRingPainter oldDelegate) => true;
}

/// Three bouncing loading dots
class _LoadingDotsPainter extends CustomPainter {
  final double progress;
  _LoadingDotsPainter({required this.progress});

  @override
  void paint(Canvas canvas, Size size) {
    final centerY = size.height / 2;
    final dotRadius = size.height * 0.18;
    final spacing = size.width / 4;

    for (int i = 0; i < 3; i++) {
      final phase = ((progress * 2 + i * 0.25) % 1.0);
      final bounce = math.sin(phase * math.pi);
      final scale = 0.5 + 0.5 * bounce;
      canvas.drawCircle(
        Offset(spacing * (i + 1), centerY - bounce * 4),
        dotRadius * scale,
        Paint()
          ..color = const Color(
            0xFF60A5FA,
          ).withValues(alpha: 0.25 + 0.75 * bounce),
      );
    }
  }

  @override
  bool shouldRepaint(_LoadingDotsPainter oldDelegate) => true;
}
