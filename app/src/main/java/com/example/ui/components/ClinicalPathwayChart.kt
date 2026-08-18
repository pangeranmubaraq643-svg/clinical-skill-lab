package com.example.ui.components

import android.graphics.Paint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ClinicalCase
import com.example.data.model.EvaluationResult
import com.example.ui.theme.EmergencyRed
import com.example.ui.theme.MedicalTeal40
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningAmber

enum class ChartTab {
    ACCURACY_VS_TIME,
    COST_EFFECTIVENESS
}

data class StageMetricPoint(
    val stageName: String,
    val timeLabel: String,
    val doctorAccuracy: Float, // 0..100
    val idealAccuracy: Float,  // 0..100
    val doctorCostRupiah: Long,
    val idealCostRupiah: Long,
    val note: String
)

@Composable
fun ClinicalPathwayMetricsCard(
    activeCase: ClinicalCase,
    evaluationResult: EvaluationResult,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(ChartTab.ACCURACY_VS_TIME) }
    var selectedPointIndex by remember { mutableStateOf<Int?>(null) }

    // Derive stage accuracy metrics based on evaluation scores
    val diagScore = if (evaluationResult.diagnosisStatus.contains("BENAR")) 100f else if (evaluationResult.diagnosisStatus.contains("KURANG")) 60f else 20f
    val pemfisScore = if (evaluationResult.pemfisStatus.contains("OPTIMAL")) 95f else if (evaluationResult.pemfisStatus.contains("KURANG")) 65f else 30f
    val treatScore = if (evaluationResult.treatmentStatus.contains("OPTIMAL")) 90f else if (evaluationResult.treatmentStatus.contains("KURANG")) 60f else 25f
    val eduScore = if (evaluationResult.educationStatus.contains("OPTIMAL")) 95f else if (evaluationResult.educationStatus.contains("KURANG")) 60f else 30f

    val totalSpent = evaluationResult.totalSpent
    val idealCost = evaluationResult.optimalCost.coerceAtLeast(50000L)

    val stagePoints = remember(evaluationResult) {
        listOf(
            StageMetricPoint(
                stageName = "Anamnesis",
                timeLabel = "0.5 m",
                doctorAccuracy = 85f,
                idealAccuracy = 95f,
                doctorCostRupiah = 0L,
                idealCostRupiah = 0L,
                note = "Kelengkapan tanya gejala utama & riwayat"
            ),
            StageMetricPoint(
                stageName = "Stabilisasi Cito",
                timeLabel = "1.2 m",
                doctorAccuracy = if (treatScore > 50) 90f else 65f,
                idealAccuracy = 95f,
                doctorCostRupiah = 0L,
                idealCostRupiah = 0L,
                note = "Penanganan awal ABCDE & resusitasi kegawatdaruratan"
            ),
            StageMetricPoint(
                stageName = "Pemfis & Lab",
                timeLabel = "2.5 m",
                doctorAccuracy = pemfisScore,
                idealAccuracy = 95f,
                doctorCostRupiah = totalSpent,
                idealCostRupiah = idealCost,
                note = "Pemeriksaan fisik & seleksi lab penunjang"
            ),
            StageMetricPoint(
                stageName = "Diagnosis",
                timeLabel = "3.8 m",
                doctorAccuracy = diagScore,
                idealAccuracy = 100f,
                doctorCostRupiah = totalSpent,
                idealCostRupiah = idealCost,
                note = "Ketepatan penetapan diagnosis kerja"
            ),
            StageMetricPoint(
                stageName = "Tatalaksana",
                timeLabel = "5.0 m",
                doctorAccuracy = (treatScore + eduScore) / 2f,
                idealAccuracy = 95f,
                doctorCostRupiah = totalSpent,
                idealCostRupiah = idealCost,
                note = "Pemberian resep obat & edukasi pasien"
            )
        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = MedicalTeal40.copy(alpha = 0.15f),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Analytics,
                                contentDescription = "Pathway Analytics",
                                tint = MedicalTeal40,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "Visualisasi Clinical Pathway",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Akurasi vs. Waktu Spent & Cost-Effectiveness",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Tab Selection
            TabRow(
                selectedTabIndex = selectedTab.ordinal,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                contentColor = MedicalTeal40,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp),
                divider = {}
            ) {
                Tab(
                    selected = selectedTab == ChartTab.ACCURACY_VS_TIME,
                    onClick = {
                        selectedTab = ChartTab.ACCURACY_VS_TIME
                        selectedPointIndex = null
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timeline,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Akurasi vs Waktu", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
                Tab(
                    selected = selectedTab == ChartTab.COST_EFFECTIVENESS,
                    onClick = {
                        selectedTab = ChartTab.COST_EFFECTIVENESS
                        selectedPointIndex = null
                    },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Payments,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Efisiensi Biaya (Lab)", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // KPI Summary Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                KpiBadgeCard(
                    title = "Skor Akurasi",
                    value = "${evaluationResult.totalScore}%",
                    subtitle = "vs 100% Ideal",
                    color = if (evaluationResult.totalScore >= 80) SuccessGreen else WarningAmber,
                    icon = Icons.Default.CheckCircle,
                    modifier = Modifier.weight(1f)
                )

                val costDiff = totalSpent - idealCost
                val costColor = if (costDiff <= 0) SuccessGreen else if (costDiff < 100000) WarningAmber else EmergencyRed
                KpiBadgeCard(
                    title = "Efisiensi Biaya",
                    value = if (costDiff <= 0) "Optimal" else "+Rp ${costDiff / 1000}rb",
                    subtitle = "vs Ideal Rp ${idealCost / 1000}rb",
                    color = costColor,
                    icon = Icons.Default.Payments,
                    modifier = Modifier.weight(1f)
                )

                KpiBadgeCard(
                    title = "Kepatuhan PPK",
                    value = if (diagScore >= 80) "Sesuai" else "Penyimpangan",
                    subtitle = "Pedoman Kemenkes",
                    color = if (diagScore >= 80) MedicalTeal40 else WarningAmber,
                    icon = Icons.Default.Speed,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Interactive Canvas Visualizer Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(8.dp)
            ) {
                if (selectedTab == ChartTab.ACCURACY_VS_TIME) {
                    AccuracyVsTimeCanvasChart(
                        stagePoints = stagePoints,
                        selectedIndex = selectedPointIndex,
                        onSelectPoint = { selectedPointIndex = it }
                    )
                } else {
                    CostEffectivenessCanvasChart(
                        stagePoints = stagePoints,
                        totalSpent = totalSpent,
                        idealCost = idealCost,
                        selectedIndex = selectedPointIndex,
                        onSelectPoint = { selectedPointIndex = it }
                    )
                }
            }

            // Legend Row
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // User line legend
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(MedicalTeal40, CircleShape)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Jalur Dokter", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)

                Spacer(modifier = Modifier.width(20.dp))

                // Ideal Benchmark legend
                Box(
                    modifier = Modifier
                        .width(16.dp)
                        .height(3.dp)
                        .background(SuccessGreen)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Pathway Ideal Kemenkes", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
            }

            // Selected Stage Detail Card (Tooltip / Note)
            selectedPointIndex?.let { idx ->
                val pt = stagePoints[idx]
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MedicalTeal40.copy(alpha = 0.08f))
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Stage ${idx + 1}: ${pt.stageName} (${pt.timeLabel})",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = MedicalTeal40
                            )
                            Text(
                                text = "Akurasi: ${pt.doctorAccuracy.toInt()}%",
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = if (pt.doctorAccuracy >= 80) SuccessGreen else WarningAmber
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pt.note,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KpiBadgeCard(
    title: String,
    value: String,
    subtitle: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.12f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 13.sp,
                color = color
            )
            Text(
                text = subtitle,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AccuracyVsTimeCanvasChart(
    stagePoints: List<StageMetricPoint>,
    selectedIndex: Int?,
    onSelectPoint: (Int) -> Unit
) {
    val primaryColor = MedicalTeal40
    val idealColor = SuccessGreen
    val gridColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .pointerInput(stagePoints) {
                detectTapGestures { offset ->
                    val paddingLeft = 80f
                    val paddingRight = 40f
                    val availableWidth = size.width - paddingLeft - paddingRight
                    val stepX = availableWidth / (stagePoints.size - 1).coerceAtLeast(1)

                    stagePoints.forEachIndexed { index, _ ->
                        val x = paddingLeft + index * stepX
                        if (kotlin.math.abs(offset.x - x) < 50f) {
                            onSelectPoint(index)
                        }
                    }
                }
            }
    ) {
        val width = size.width
        val height = size.height

        val paddingLeft = 80f
        val paddingRight = 40f
        val paddingTop = 30f
        val paddingBottom = 40f

        val chartWidth = width - paddingLeft - paddingRight
        val chartHeight = height - paddingTop - paddingBottom

        // Draw horizontal grid lines (0%, 25%, 50%, 75%, 100%)
        val gridSteps = 4
        for (i in 0..gridSteps) {
            val y = paddingTop + (chartHeight / gridSteps) * i
            drawLine(
                color = gridColor,
                start = Offset(paddingLeft, y),
                end = Offset(width - paddingRight, y),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
            )

            // Y-axis label text
            val pctValue = 100 - (i * 25)
            drawContext.canvas.nativeCanvas.drawText(
                "$pctValue%",
                paddingLeft - 15f,
                y + 10f,
                Paint().apply {
                    this.color = textColor
                    textSize = 22f
                    textAlign = Paint.Align.RIGHT
                    isAntiAlias = true
                }
            )
        }

        val stepX = chartWidth / (stagePoints.size - 1).coerceAtLeast(1)

        // Plot Ideal Pathway curve (Dashed line)
        val idealPath = Path()
        stagePoints.forEachIndexed { index, pt ->
            val x = paddingLeft + index * stepX
            val y = paddingTop + chartHeight * (1f - pt.idealAccuracy / 100f)
            if (index == 0) idealPath.moveTo(x, y) else idealPath.lineTo(x, y)
        }
        drawPath(
            path = idealPath,
            color = idealColor,
            style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 8f)))
        )

        // Plot Doctor Pathway curve (Smooth Bézier line)
        val docPoints = stagePoints.mapIndexed { index, pt ->
            val x = paddingLeft + index * stepX
            val y = paddingTop + chartHeight * (1f - pt.doctorAccuracy / 100f)
            Offset(x, y)
        }

        val doctorPath = Path()
        val areaPath = Path()

        if (docPoints.isNotEmpty()) {
            doctorPath.moveTo(docPoints[0].x, docPoints[0].y)
            areaPath.moveTo(docPoints[0].x, paddingTop + chartHeight)
            areaPath.lineTo(docPoints[0].x, docPoints[0].y)

            for (i in 0 until docPoints.size - 1) {
                val p1 = docPoints[i]
                val p2 = docPoints[i + 1]
                val cx1 = p1.x + (p2.x - p1.x) / 2f
                val cy1 = p1.y
                val cx2 = p1.x + (p2.x - p1.x) / 2f
                val cy2 = p2.y
                doctorPath.cubicTo(cx1, cy1, cx2, cy2, p2.x, p2.y)
                areaPath.cubicTo(cx1, cy1, cx2, cy2, p2.x, p2.y)
            }

            val lastPt = docPoints.last()
            areaPath.lineTo(lastPt.x, paddingTop + chartHeight)
            areaPath.close()

            // Draw translucent gradient under user line
            drawPath(
                path = areaPath,
                brush = Brush.verticalGradient(
                    colors = listOf(primaryColor.copy(alpha = 0.35f), primaryColor.copy(alpha = 0.02f)),
                    startY = paddingTop,
                    endY = paddingTop + chartHeight
                )
            )

            // Draw Doctor line
            drawPath(
                path = doctorPath,
                color = primaryColor,
                style = Stroke(width = 5f, cap = StrokeCap.Round)
            )
        }

        // Draw Stage Point markers & X-axis labels
        docPoints.forEachIndexed { index, ptOffset ->
            val isSelected = selectedIndex == index
            val pointRadius = if (isSelected) 12f else 8f

            // Outer pulse circle if selected
            if (isSelected) {
                drawCircle(
                    color = primaryColor.copy(alpha = 0.25f),
                    radius = 22f,
                    center = ptOffset
                )
            }

            // Circle point
            drawCircle(
                color = if (isSelected) Color.White else primaryColor,
                radius = pointRadius,
                center = ptOffset
            )
            drawCircle(
                color = primaryColor,
                radius = pointRadius - 3f,
                style = Stroke(width = 3f),
                center = ptOffset
            )

            // X-axis Stage label
            val stage = stagePoints[index]
            drawContext.canvas.nativeCanvas.drawText(
                stage.stageName,
                ptOffset.x,
                height - 10f,
                Paint().apply {
                    this.color = textColor
                    textSize = 22f
                    textAlign = Paint.Align.CENTER
                    isAntiAlias = true
                    isFakeBoldText = isSelected
                }
            )

            // Accuracy value above point
            drawContext.canvas.nativeCanvas.drawText(
                "${stage.doctorAccuracy.toInt()}%",
                ptOffset.x,
                ptOffset.y - 14f,
                Paint().apply {
                    this.color = primaryColor.toArgb()
                    textSize = 20f
                    textAlign = Paint.Align.CENTER
                    isFakeBoldText = true
                    isAntiAlias = true
                }
            )
        }
    }
}

@Composable
private fun CostEffectivenessCanvasChart(
    stagePoints: List<StageMetricPoint>,
    totalSpent: Long,
    idealCost: Long,
    selectedIndex: Int?,
    onSelectPoint: (Int) -> Unit
) {
    val userColor = MedicalTeal40
    val idealColor = SuccessGreen
    val gridColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.3f)
    val textColor = MaterialTheme.colorScheme.onSurface.toArgb()

    val maxCost = maxOf(totalSpent, idealCost, 250000L).toFloat()

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        val width = size.width
        val height = size.height

        val paddingLeft = 100f
        val paddingRight = 40f
        val paddingTop = 30f
        val paddingBottom = 40f

        val chartWidth = width - paddingLeft - paddingRight
        val chartHeight = height - paddingTop - paddingBottom

        // Draw horizontal grid lines
        val gridSteps = 3
        for (i in 0..gridSteps) {
            val y = paddingTop + (chartHeight / gridSteps) * i
            drawLine(
                color = gridColor,
                start = Offset(paddingLeft, y),
                end = Offset(width - paddingRight, y),
                strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
            )

            val costLabelVal = (maxCost / 1000f) * (1f - i.toFloat() / gridSteps)
            drawContext.canvas.nativeCanvas.drawText(
                "Rp ${costLabelVal.toInt()}rb",
                paddingLeft - 15f,
                y + 8f,
                Paint().apply {
                    this.color = textColor
                    textSize = 20f
                    textAlign = Paint.Align.RIGHT
                    isAntiAlias = true
                }
            )
        }

        // Draw Comparison Bars: Doctor Spend vs Ideal Spend
        val barGroupWidth = chartWidth / 2f
        val barWidth = 50f

        // Bar 1: Dokter Total Spent
        val x1 = paddingLeft + barGroupWidth / 2f - barWidth / 2f - 40f
        val doctorHeight = (totalSpent.toFloat() / maxCost) * chartHeight
        val y1 = paddingTop + (chartHeight - doctorHeight)

        drawRoundRect(
            color = userColor,
            topLeft = Offset(x1, y1),
            size = androidx.compose.ui.geometry.Size(barWidth, doctorHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
        )

        drawContext.canvas.nativeCanvas.drawText(
            "Pengeluaran Dokter",
            x1 + barWidth / 2f,
            height - 10f,
            Paint().apply {
                this.color = textColor
                textSize = 20f
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                isFakeBoldText = true
            }
        )

        drawContext.canvas.nativeCanvas.drawText(
            "Rp ${totalSpent / 1000}rb",
            x1 + barWidth / 2f,
            y1 - 10f,
            Paint().apply {
                this.color = userColor.toArgb()
                textSize = 22f
                textAlign = Paint.Align.CENTER
                isFakeBoldText = true
                isAntiAlias = true
            }
        )

        // Bar 2: Ideal Pathway Cost
        val x2 = paddingLeft + barGroupWidth + barGroupWidth / 2f - barWidth / 2f - 40f
        val idealHeight = (idealCost.toFloat() / maxCost) * chartHeight
        val y2 = paddingTop + (chartHeight - idealHeight)

        drawRoundRect(
            color = idealColor,
            topLeft = Offset(x2, y2),
            size = androidx.compose.ui.geometry.Size(barWidth, idealHeight),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(10f, 10f)
        )

        drawContext.canvas.nativeCanvas.drawText(
            "Target Pathway Ideal",
            x2 + barWidth / 2f,
            height - 10f,
            Paint().apply {
                this.color = textColor
                textSize = 20f
                textAlign = Paint.Align.CENTER
                isAntiAlias = true
                isFakeBoldText = true
            }
        )

        drawContext.canvas.nativeCanvas.drawText(
            "Rp ${idealCost / 1000}rb",
            x2 + barWidth / 2f,
            y2 - 10f,
            Paint().apply {
                this.color = idealColor.toArgb()
                textSize = 22f
                textAlign = Paint.Align.CENTER
                isFakeBoldText = true
                isAntiAlias = true
            }
        )
    }
}
