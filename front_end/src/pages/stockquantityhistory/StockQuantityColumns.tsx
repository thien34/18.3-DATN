import { StockQuantityHistoryResponse } from '@/model/StockQuantityHistory'
import formatDate from '@/utils/DateConvertUtils'
import { TableColumnsType } from 'antd'

const getStockQuantityColumns = (): TableColumnsType<StockQuantityHistoryResponse> => [
    {
        title: 'Attributes Combianation',
        dataIndex: 'attributesXml',
        key: 'attributesXml',
        render: (attributesXml: string) => {
            try {
                const parser = new DOMParser();
                const xmlDoc = parser.parseFromString(attributesXml, "text/xml");
            
                const attributes = Array.from(xmlDoc.getElementsByTagName('attributes')[0].children);
                
                if (!attributes || !Array.isArray(attributes) || attributes.length === 0) {
                    return null;
                }
            
                return (
                    <div>
                        {attributes.map((attr) => {
                            const key = attr.tagName; 
                            const value = attr.textContent; 
                            return (
                                <div key={key}>
                                    <strong>{key ? key.charAt(0).toUpperCase() + key.slice(1).toLowerCase() + ':' : ''}</strong> {value || ''}
                                </div>
                            );
                        })}
                    </div>
                );
            } catch (error) {
                return 'Default';
            }
        },
    },
    {
        width: '10%',
        title: 'QuantityAdjustment',
        dataIndex: 'quantityAdjustment',
        key: 'quantityAdjustment',
        render: (quantityAdjustment) => (quantityAdjustment > 0 ? `+${quantityAdjustment}` : `${quantityAdjustment}`),
    },
    {
        width: '10%',
        title: 'StockQuantity',
        dataIndex: 'stockQuantity',
        key: 'stockQuantity',
    },
    {
        width: '30%',
        title: 'Message',
        dataIndex: 'message',
        key: 'message',
    },
    {
        width: '25%',
        title: 'Created Date',
        dataIndex: 'createdDate',
        key: 'createdDate',
        render: (createdDate: Date) => formatDate(createdDate),
    },
]
export default getStockQuantityColumns
