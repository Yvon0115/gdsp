package com.gdsp.dev.core.legalize.core;

import com.gdsp.dev.core.legalize.api.LegalizeInfoAwre;
import com.gdsp.dev.core.legalize.model.LegalizeInfo;

public class LegalizeAwreSupport implements LegalizeInfoAwre {
	/**
	 * 证书包括的信息
	 */
	protected LegalizeInfo legalizeInfo;
	
	@Override
	public void setLegalizeInfo(LegalizeInfo info) {
		this.legalizeInfo = info;
	}

}
