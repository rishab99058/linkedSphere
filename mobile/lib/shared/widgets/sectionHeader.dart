import 'package:flutter/material.dart';
import 'package:mobile/core/colors.dart';

/// A modern, beautiful reusable section header widget for forms and profile screens.
/// Features a card-style container with soft gradients, subtle borders, and an icon badge.
class SectionHeader extends StatelessWidget {
  final IconData icon;
  final String title;
  final String? subtitle;
  final Color? iconColor;
  final Color? iconBackgroundColor;
  final Color? backgroundColor;
  final Color? borderColor;
  final bool useCardStyle;
  final EdgeInsetsGeometry margin;
  final EdgeInsetsGeometry padding;
  final Widget? trailing;

  const SectionHeader({
    super.key,
    required this.icon,
    required this.title,
    this.subtitle,
    this.iconColor,
    this.iconBackgroundColor,
    this.backgroundColor,
    this.borderColor,
    this.useCardStyle = true,
    this.margin = const EdgeInsets.symmetric(horizontal: 16.0, vertical: 6.0),
    this.padding = const EdgeInsets.symmetric(horizontal: 16.0, vertical: 14.0),
    this.trailing,
  });

  @override
  Widget build(BuildContext context) {
    final effectiveIconColor = iconColor ?? AppColors.primary;
    final effectiveIconBgColor =
        iconBackgroundColor ?? effectiveIconColor.withValues(alpha: 0.10);

    final content = Row(
      crossAxisAlignment: CrossAxisAlignment.center,
      children: [
        // -------------------------------------------------------------
        // ICON BADGE WITH BORDER & GLOW
        // -------------------------------------------------------------
        Container(
          height: 46,
          width: 46,
          decoration: BoxDecoration(
            color: effectiveIconBgColor,
            borderRadius: BorderRadius.circular(14),
            border: Border.all(
              color: effectiveIconColor.withValues(alpha: 0.20),
              width: 1.2,
            ),
            boxShadow: [
              BoxShadow(
                color: effectiveIconColor.withValues(alpha: 0.08),
                blurRadius: 10,
                offset: const Offset(0, 3),
              ),
            ],
          ),
          child: Icon(
            icon,
            color: effectiveIconColor,
            size: 23,
          ),
        ),

        const SizedBox(width: 14),

        // -------------------------------------------------------------
        // TITLE & SUBTITLE
        // -------------------------------------------------------------
        Expanded(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                title,
                style: const TextStyle(
                  fontSize: 16.5,
                  fontWeight: FontWeight.w800,
                  color: Color(0xFF102A56),
                  letterSpacing: -0.3,
                ),
              ),
              if (subtitle != null && subtitle!.trim().isNotEmpty) ...[
                const SizedBox(height: 3),
                Text(
                  subtitle!,
                  style: const TextStyle(
                    fontSize: 12.5,
                    height: 1.3,
                    color: Color(0xFF64748B),
                    fontWeight: FontWeight.w400,
                  ),
                ),
              ],
            ],
          ),
        ),

        // Optional Trailing Widget
        ?trailing,
      ],
    );

    if (!useCardStyle) {
      return Padding(
        padding: margin,
        child: content,
      );
    }

    return Container(
      margin: margin,
      padding: padding,
      decoration: BoxDecoration(
        color: backgroundColor ?? const Color(0xFFF8FBFF),
        borderRadius: BorderRadius.circular(18),
        border: Border.all(
          color: borderColor ?? const Color(0xFFE2E8F0),
          width: 1.2,
        ),
        boxShadow: [
          BoxShadow(
            color: const Color(0xFF1677FF).withValues(alpha: 0.035),
            blurRadius: 14,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: content,
    );
  }
}
