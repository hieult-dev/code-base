export const DEFAULT_PAGE_SIZE_OPTIONS = [5, 10, 25, 50];
export const DEFAULT_PAGE_SIZE = 20;
export const DEFAULT_SCROLL_HEIGHT = 'calc(100vh - 9rem)';
export const DEFAULT_PAGINATOR_TEMPLATE =
    'CurrentPageReport FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown';
export const DEFAULT_DATATABLE_CONFIG = {
    paginator: true,

    currentPageReportTemplate:
        'Hiển thị {first} - {last} / {totalRecords}',

    paginatorTemplate: DEFAULT_PAGINATOR_TEMPLATE,

    rows: DEFAULT_PAGE_SIZE,
    rowsPerPageOptions: DEFAULT_PAGE_SIZE_OPTIONS,

    showGridlines: false,
    lazy: true,

    scrollable: true,
    scrollHeight: DEFAULT_SCROLL_HEIGHT,

    stripedRows: false,
    rowHover: true,

    className: 'h-full'
}