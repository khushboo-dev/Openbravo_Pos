//    Openbravo POS is a point of sales application designed for touch screens.
//    Copyright (C) 2007-2011 Openbravo, S.L.
//    http://www.openbravo.com/product/pos
//
//    This file is part of Openbravo POS.
//
//    Openbravo POS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Openbravo POS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Openbravo POS.  If not, see <http://www.gnu.org/licenses/>.

package com.openbravo.data.loader;

import com.openbravo.basic.BasicException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

public final class PreparedSentenceParams implements DataWrite {

    private PreparedStatement m_ps;

    PreparedSentenceParams(PreparedStatement ps) {
        m_ps = ps;
    }

    public void setInt(int paramIndex, Integer iValue) throws BasicException {
        try {
            m_ps.setObject(paramIndex, iValue, Types.INTEGER);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    public void setString(int paramIndex, String sValue) throws BasicException {
        try {
            m_ps.setString(paramIndex, sValue);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    public void setDouble(int paramIndex, Double dValue) throws BasicException {
        try {
            m_ps.setObject(paramIndex, dValue, Types.DOUBLE);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    public void setBoolean(int paramIndex, Boolean bValue) throws BasicException {
        try {
            if (bValue == null) {
                m_ps.setString(paramIndex, null);
            } else {
                m_ps.setString(paramIndex, bValue.booleanValue() ? "Y" : "N");
            }
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    public void setTimestamp(int paramIndex, java.util.Date dValue) throws BasicException {
        try {
            m_ps.setObject(paramIndex, dValue == null ? null : new Timestamp(dValue.getTime()), Types.TIMESTAMP);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    //        public void setBinaryStream(int paramIndex, java.io.InputStream in, int length) throws DataException {
    //            try {
    //                m_ps.setBinaryStream(paramIndex, in, length);
    //            } catch (SQLException eSQL) {
    //                throw new DataException(eSQL);
    //            }
    //        }
    public void setBytes(int paramIndex, byte[] value) throws BasicException {
        try {
            m_ps.setBytes(paramIndex, value);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }

    public void setObject(int paramIndex, Object value) throws BasicException {
        try {
            m_ps.setObject(paramIndex, value);
        } catch (SQLException eSQL) {
            throw new BasicException(eSQL);
        }
    }
}
