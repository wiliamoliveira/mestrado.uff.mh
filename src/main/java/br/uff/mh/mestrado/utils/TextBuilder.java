package br.uff.mh.mestrado.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class TextBuilder {
	public static StringBuilder getFistFrameOfThrowable(Throwable t) {
		StringBuilder sb = new StringBuilder();

		if (t != null) {
			
			sb.append('[');
			sb.append(t.getClass());
			sb.append(']');
			
			sb.append(" : [");
			sb.append(t.getMessage());
			sb.append(']');

			String[] frames = ExceptionUtils.getStackFrames(t);
			if (frames.length > 1) {
				sb.append(" >> [");
				sb.append(StringUtils.stripToNull(frames[1]));
				sb.append(']');
			}
		}

		return sb;
	}
	
	public static StringBuilder getFistFrameOfThrowable(Throwable t, StringBuilder info) {
		StringBuilder sb = new StringBuilder();

		if (t != null) {
			
			sb.append('[');
			sb.append(t.getClass());
			sb.append(']');
			
			sb.append(" : [");
			sb.append(t.getMessage());
			sb.append(']');
			
			sb.append(" : [");
			sb.append(info);
			sb.append(']');

			String[] frames = ExceptionUtils.getStackFrames(t);
			if (frames.length > 1) {
				sb.append(" >> [");
				sb.append(StringUtils.stripToNull(frames[1]));
				sb.append(']');
			}
		}

		return sb;
	}
}
