/*
 * Copyright (c) 2011-2015, Peter Abeles. All Rights Reserved.
 *
 * This file is part of BoofCV (http://boofcv.org).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package boofcv.factory.shape;

import boofcv.struct.Configuration;

/**
 * Configuration for {@link boofcv.alg.shapes.polygon.BinaryPolygonConvexDetector} for use in {@link FactoryShapeDetector}.
 *
 * @author Peter Abeles
 */
public class ConfigPolygonDetector implements Configuration {

	/**
	 * Number of sides in the polygon it's trying to detect
	 */
	public int[] numberOfSides = null;

	/**
	 * Two lines are merged together if their angle is &le; to this number.  This variable is dependent upon the
	 * number of sides.  The more sides you wish to detect the more likely you will need to reduce this number.
	 */
	public double contour2Poly_splitFraction = 0.15;

	/**
	 * Number of split and merge iterations when converting contour into polygon
	 */
	public int contour2Poly_iterations = 20;

	/**
	 * The minimum number of pixels away a contour point is from a line to cause a split/merge.  Specified as a fraction
	 * of total image width.
	 */
	public double contour2Poly_minimumSplitFraction = 0.0078125;

	/**
	 * Subpixel refinement using lines.  This assumes that the lines are straight so it works best when lens
	 * distortion has been removed.
	 */
	public boolean refineWithLines = true;

	/**
	 * Subpixel refinement using corners.  Doesn't require straight lines so it is more tolerant of distortion.
	 * However not as much information is being used so the fit might not be as good under ideal situations.
	 */
	public boolean refineWithCorners = false;

	/**
	 * <p>
	 * The minimum allowed edge intensity for a shape.  Used to remove false positives generated by noise, which
	 * is especially common when using a local threshold during binarization.
	 * </p>
	 *
	 * <p>Set to zero to disable.</p>
	 *
	 * @see boofcv.alg.shapes.edge.PolygonEdgeScore
	 */
	public double minimumEdgeIntensity = 3.0;

	/**
	 * Specifies the minimum allowed contour length as a fraction of the input image's width.  Smaller numbers
	 * mean smaller objects are allowed.
	 */
	public double minContourImageWidthFraction = 0.05;

	/**
	 * Will the found polygons be in clockwise order?
	 */
	public boolean clockwise = true;

	/**
	 * Configuration for refining with lines.  Ignored if not used.
	 */
	public ConfigRefinePolygonLineToImage configRefineLines = new ConfigRefinePolygonLineToImage();

	/**
	 * Configuration for refining with corners.  Ignored if not used.
	 */
	public ConfigRefinePolygonCornersToImage configRefineCorners = new ConfigRefinePolygonCornersToImage();

	/**
	 * Specifies the number of sides in the polygon and uses default settings for everything else
	 */
	public ConfigPolygonDetector(int ...numberOfSides) {
		this.numberOfSides = numberOfSides;
	}

	public ConfigPolygonDetector(boolean clockwise, int ...numberOfSides) {
		this.numberOfSides = numberOfSides;
		this.clockwise = clockwise;
	}

	@Override
	public void checkValidity() {
		if( (refineWithCorners && refineWithLines) ) {
			throw new IllegalArgumentException("Can't refine with both corners and lines");
		}
	}

	@Override
	public String toString() {
		String sides = "[";
		for (int i = 0; i < numberOfSides.length; i++) {
			sides += " "+numberOfSides[i];
		}
		sides += " ]";

		return getClass().getSimpleName()+"{ numberOfSides="+sides+
				" , contour2Poly_splitFraction="+contour2Poly_splitFraction+
				" , contour2Poly_iterations="+contour2Poly_iterations+
				" , contour2Poly_minimumSplitFraction="+ contour2Poly_minimumSplitFraction +
				" , refineWithLines="+refineWithLines+
				" , refineWithCorners="+refineWithCorners+
				" , minContourImageWidthFraction="+minContourImageWidthFraction+
				" , clockwise="+clockwise+
				" , configRefineLines="+configRefineLines+
				" , configRefineCorners="+configRefineCorners+" }";
	}
}
